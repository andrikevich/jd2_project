package it.academy.cv_storage.data.utilities;

import javax.persistence.Table;

import it.academy.cv_storage.exception.ClassHasNoCorrectAnnotation;

public class ClassInfoRetriever {

	public static String getEntityTableName(Class<?> className) throws ClassHasNoCorrectAnnotation {
		if (className.isAnnotationPresent(Table.class)) {
			if ((className.getAnnotation(Table.class)).name().length() == 0) {
				return className.getSimpleName().toLowerCase();
			} else {
				return className.getAnnotation(Table.class).name();
			}
		} else 
		
		throw new ClassHasNoCorrectAnnotation
		("there is no annotation @Table in class: " + className.getName());
	}
}
