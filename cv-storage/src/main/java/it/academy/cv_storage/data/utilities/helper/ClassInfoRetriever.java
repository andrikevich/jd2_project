package it.academy.cv_storage.data.utilities.helper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import it.academy.cv_storage.data.utilities.criteria.agregation.Aggregator;

import it.academy.cv_storage.exception.ClassHasNoCorrectAnnotation;
import it.academy.cv_storage.exception.IncorrectArgumentException;
import it.academy.cv_storage.exception.NullClassEntityExeption;
import it.academy.cv_storage.exception.StartSqlSentenceExeption;


public class ClassInfoRetriever {

	private Class<?> className;

	public ClassInfoRetriever(Class<?> className) throws ClassHasNoCorrectAnnotation, NullClassEntityExeption {
		
		if (className == null) {
			throw new NullClassEntityExeption("You are trying to retrieve data from null class-entity");
		}
		if (!className.isAnnotationPresent(Table.class)) {
			throw new ClassHasNoCorrectAnnotation("There is no annotation @Table in class: " + className.getName());
		}
		this.className = className;
	}

	/*
	 * if class have @Annotation @Table with parameter = name of table in DB it will
	 * be returned table name else as default it will be returned class name
	 */
	public String getEntityTableName() throws ClassHasNoCorrectAnnotation, NullClassEntityExeption {
		String nameFromAnnot = className.getAnnotation(Table.class).name();
			if (nameFromAnnot ==null || nameFromAnnot.length() == 0) {
				return nameFromAnnot;
			} else {
				return className.getSimpleName().toLowerCase();
			}
	}

	public List<String> getSelectParameters(String... searchParam) throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, IncorrectArgumentException {
		List <String> result = new LinkedList<String>();

				if ( searchParam== null) {
					throw new IncorrectArgumentException(
							"There is a null in a parameter array");
				}

		for (String tmpParam : searchParam) {
			result.add(getSelectParameter(className,tmpParam));
		}
			return result;
	}

	
	public String getSelectParameter(Class className,String searchParam) throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, IncorrectArgumentException {
		
		Field[] fields = className.getDeclaredFields();
		List<Class> classesWithOneOrManyToMany = Arrays.stream(fields)
				.filter(x -> x.isAnnotationPresent(OneToMany.class) )
				.map(field -> field.getAnnotation(OneToMany.class).targetEntity())
				.collect(Collectors.toList());

		classesWithOneOrManyToMany.addAll(Arrays.stream(fields)
				.filter(x -> x.isAnnotationPresent(ManyToMany.class) )
				.map(field -> field.getAnnotation(ManyToMany.class).targetEntity())
				.collect(Collectors.toList()));
		
		if(className != null) {
			
				
				if (searchParam == null) {
					throw new IncorrectArgumentException(
							"The search parameter is null");
				}
				
				/*  If inputted by user parameter is in @annotation of field/column of class
				 * it will return name of this annotation
				 * else it return field name from class*/

			//looking for correct annotation
				if(Arrays.stream(fields)
						 .map(field->field.getAnnotation(Column.class))
						 .filter(colunm->colunm != null)
						 .anyMatch(column ->column.name().toLowerCase().trim()
								 				.equals(searchParam.toLowerCase().trim()))) {
					return searchParam.toUpperCase();
				} else if(Arrays.stream(fields) // looking for correct field in class
								.anyMatch(field->field.getName()
										.toLowerCase().trim()
										.equals(searchParam.toLowerCase().trim()))) {
					
					//field  case sensitive otherwise 
					// it will be NullPointerExeption in Column colAnnot = field.getAnnotation(Column.class);
					Field field = findFieldIgnoreCase(className, searchParam);
					Column colAnnot = field.getAnnotation(Column.class);
					
					if (colAnnot != null) {
						return colAnnot.name().toUpperCase();
					}else
					return field.getName().toUpperCase();					
				}else if(classesWithOneOrManyToMany.stream().anyMatch(clz->isSuchFieldInClass(clz,searchParam))) {
								Class classWithParam = classesWithOneOrManyToMany.stream()
																			.filter(clz -> isSuchFieldInClass(clz, searchParam))
																			.findFirst()
																			.get();
					//recursive for one to many
				 return 	getSelectParameter(classWithParam,searchParam);
				}
					throw new IncorrectArgumentException(
						"There were inputed incorrect parameter for SQL query. Inserted parameter: <<<" + searchParam + ">>>");
		}else
				throw new StartSqlSentenceExeption("You haven't insert class name to search from");
	}

	
	private Field findFieldIgnoreCase(Class<?> clazz, String fieldName) throws SecurityException, NoSuchFieldException {
	    try {
	        return clazz.getDeclaredField(fieldName);
	    } catch (NoSuchFieldException e) {
	        Field[] fields = clazz.getDeclaredFields();
	        for (Field field : fields) {
	            if (field.getName().equalsIgnoreCase(fieldName)) {
	                return field;
	            }
	        }
	        throw new NoSuchFieldException(fieldName);
	    }
	}

	public String getSelectAggParameter(Aggregator aggSearchParam) throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, IncorrectArgumentException {
		String searchParam = aggSearchParam.getParamName();
		Field[] fields = className.getDeclaredFields();

		if(className != null) {


			if (searchParam == null) {
				throw new IncorrectArgumentException(
						"The search parameter is null");
			}

			/*  If inputed by user parameter is in @annotation of field/column of class
			 * it will return name of this annotation
			 * else it return field name from class*/
			if(Arrays.stream(fields)
					.map(field->field.getAnnotation(Column.class))
					.filter(colunm->colunm != null)
					.anyMatch(column ->column.name().toLowerCase().trim()
							.equals(searchParam.toLowerCase().trim()))) {
				return aggSearchParam.getAggrFunc() + "(" + searchParam.toUpperCase() + ")";
			} else if(Arrays.stream(fields)
					.anyMatch(field->field.getName()
							.toLowerCase().trim()
							.equals(searchParam.toLowerCase().trim()))) {

				//field  case sensitive otherwise
				// it will be NullPointerExeption in Column colAnnot = field.getAnnotation(Column.class);
				Field field = findFieldIgnoreCase(className, searchParam);
				Column colAnnot = field.getAnnotation(Column.class);

				if (colAnnot != null) {
					return aggSearchParam.getAggrFunc() + "(" +  colAnnot.name().toUpperCase() + ")";
				}else
					return aggSearchParam.getAggrFunc() + "(" + field.getName().toUpperCase() + ")";
			}else
				throw new IncorrectArgumentException(
						"There were inputed incorrect parameter for SQL query. Inserted parameter: <<<" + searchParam + ">>>");
		}else
			throw new StartSqlSentenceExeption("You haven't insert class name to search from");
	}

	public List<String> getSelectAggParameters(List<Aggregator> aggSearchParam) throws StartSqlSentenceExeption, NoSuchFieldException, IncorrectArgumentException {
		List<String> result = new ArrayList<>();
		for (Aggregator aggregator : aggSearchParam) {
			result.add(getSelectAggParameter(aggregator));
		}
		return result;
	}


	//---------------
	private   boolean isSuchFieldInClass(Class clazz, String searchParam){
		Field[] fields = clazz.getDeclaredFields();
		final boolean isCorrectAnnot = Arrays.stream(fields).map(field -> field.getAnnotation(Column.class))
				.filter(colunm -> colunm != null)
				.anyMatch(column -> column.name().toLowerCase().trim()
						.equals(searchParam.toLowerCase().trim()));
		boolean isFieldIsPresent = Arrays.stream(fields).anyMatch(field -> field.getName()
				.toLowerCase().trim()
				.equals(searchParam.toLowerCase().trim()));
		return ( isCorrectAnnot || isFieldIsPresent);
	}
}
