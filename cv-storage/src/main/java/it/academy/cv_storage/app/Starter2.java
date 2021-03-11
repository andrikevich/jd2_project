package it.academy.cv_storage.app;

import it.academy.cv_storage.model.entity.Candidate;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Starter2 {

    public static void main(String[] args) {
        String searchParam = "EMAIL_ADRESS";
        Class<Candidate> clazz = Candidate.class;
        Field[] fields = clazz.getDeclaredFields();

		String result = null;
		List<Class> collect = Arrays.stream(fields)
				.filter(x -> x.isAnnotationPresent(OneToMany.class))
				.map(field -> field.getAnnotation(OneToMany.class).targetEntity())
				.collect(Collectors.toList());

		for (Class aClass : collect) {
			if(isSuchFieldInClass(aClass,searchParam))
				result =searchParam.toUpperCase();
		}

		System.out.println(result);



    }

    public  static boolean isSuchFieldInClass(Class clazz, String searchParam){
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
