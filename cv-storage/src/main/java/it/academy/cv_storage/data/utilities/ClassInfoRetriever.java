package it.academy.cv_storage.data.utilities;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;

import it.academy.cv_storage.exception.ClassHasNoCorrectAnnotation;
import it.academy.cv_storage.exception.IncorrectArgumentException;
import it.academy.cv_storage.exception.StartSqlSentenceExeption;
import it.academy.cv_storage.model.entity.Candidate;

public class ClassInfoRetriever {

	private Class<?> className;

	public ClassInfoRetriever(Class<?> className) {
		this.className = className;
	}

	/*
	 * if class have @Annotation @Table with parameter = name of table in DB it will
	 * be returned table name else as default it will be returned class name
	 */
	public String getEntityTableName() throws ClassHasNoCorrectAnnotation {
		if (className.isAnnotationPresent(Table.class)) {
			if ((className.getAnnotation(Table.class)).name().length() == 0) {
				return className.getAnnotation(Table.class).name();

			} else {
				return className.getSimpleName().toLowerCase();
			}
		} else

			throw new ClassHasNoCorrectAnnotation("there is no annotation @Table in class: " + className.getName());
	}

	public List<String> getSelectParameters(String... searchParam) throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, IncorrectArgumentException {
		List <String> result = new LinkedList<String>();
		Field[] fields = className.getDeclaredFields();
		
		if(className != null) {
			for(String param : searchParam) {
				
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
					Field field = className.getDeclaredField(param);
					Column colAnnot = field.getAnnotation(Column.class);
					if (colAnnot != null) {
						result.add(colAnnot.name().toUpperCase());
					}else
					result.add(field.getName().toUpperCase());					
				}else 
					throw new IncorrectArgumentException(
						"There were inputed incorrect parameter for SQL query. Insertwed parameter: " + param);
				
			}
			

			return result;
		}
		throw new StartSqlSentenceExeption("You haven't insert class nafe to search from");


		
	}
}
