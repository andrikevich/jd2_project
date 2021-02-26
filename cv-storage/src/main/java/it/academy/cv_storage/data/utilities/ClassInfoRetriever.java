package it.academy.cv_storage.data.utilities;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;

import it.academy.cv_storage.data.utilities.agregation.Aggregator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import it.academy.cv_storage.exception.ClassHasNoCorrectAnnotation;
import it.academy.cv_storage.exception.IncorrectArgumentException;
import it.academy.cv_storage.exception.NullClassEntityExeption;
import it.academy.cv_storage.exception.StartSqlSentenceExeption;
import it.academy.cv_storage.model.entity.Candidate;


public class ClassInfoRetriever {

	private Class<?> className;

	public ClassInfoRetriever(Class<?> className) throws ClassHasNoCorrectAnnotation, NullClassEntityExeption {
		
		if (className == null) {
			throw new NullClassEntityExeption("You are thrying to retrive data from null class-entity");
		}
		if (!className.isAnnotationPresent(Table.class)) {
			throw new ClassHasNoCorrectAnnotation("there is no annotation @Table in class: " + className.getName());
		}
		this.className = className;
	}

	/*
	 * if class have @Annotation @Table with parameter = name of table in DB it will
	 * be returned table name else as default it will be returned class name
	 */
	public String getEntityTableName() throws ClassHasNoCorrectAnnotation, NullClassEntityExeption {

		
			if ((className.getAnnotation(Table.class)).name().length() == 0) {
				return className.getAnnotation(Table.class).name();

			} else {
				return className.getSimpleName().toLowerCase();
			}
	}

	public List<String> getSelectParameters(String... searchParam) throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, IncorrectArgumentException {
		List <String> result = new LinkedList<String>();
		Field[] fields = className.getDeclaredFields();
		
		if(className != null) {
			for(String param : searchParam) {
				
				if (param == null) {
					throw new IncorrectArgumentException(
							"There is a null in a parameter array");
				}
				
				/*  If inputed by user parameter is in @annotation of field/column of class
				 * it will return name of this annotation
				 * else it return field name from class*/
				if(Arrays.stream(fields)
						 .map(field->field.getAnnotation(Column.class))
						 .filter(colunm->colunm != null)
						 .anyMatch(column ->column.name().toLowerCase().trim()
								 				.equals(param.toLowerCase().trim()))) {
					result.add(param.toUpperCase());
				} else if(Arrays.stream(fields)
								.anyMatch(field->field.getName()
										.toLowerCase().trim()
										.equals(param.toLowerCase().trim()))) {
					Field field = findFieldIgnoreCase(className, param);
					Column colAnnot = field.getAnnotation(Column.class);
					if (colAnnot != null) {
						result.add(colAnnot.name().toUpperCase());
					}else
					result.add(field.getName().toUpperCase());					
				}else 
					throw new IncorrectArgumentException(
						"There were inputed incorrect parameter for SQL query. Inserted parameter: <<<" + param + ">>>");
				
			}
			

			return result;
		}
		throw new StartSqlSentenceExeption("You haven't insert class nafe to search from");


		
	}
	
	
	public String getSelectParameter(String searchParam) throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, IncorrectArgumentException {
		
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
					return searchParam.toUpperCase();
				} else if(Arrays.stream(fields)
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
				}else 
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
}
