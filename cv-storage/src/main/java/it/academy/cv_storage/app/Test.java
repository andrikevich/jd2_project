package it.academy.cv_storage.app;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

import javax.persistence.Column;

import it.academy.cv_storage.model.entity.Candidate;

public class Test {
	
	public static void main(String[] args)   {

		
		try {
			Field[] fields = Candidate.class.getDeclaredFields();
			
//			 boolean isThereAnnot =	Arrays.stream(fields)
//					 .map(field->field.getAnnotation(Column.class))
//					 .filter(colunm->colunm != null)
//					 .anyMatch(column ->column.name().toLowerCase().trim()
//							 				.equals("firSt_name".toLowerCase().trim()));
//            System.out.println(isThereAnnot);
//            System.out.println("---------------------");
//			 
			 
				Arrays.stream(fields)
					 .map(field->field.getName())
					 .forEach(System.out::println);
				
//			for(Field field : fields) {
//				System.out.println("field name: " + field.getName());
//				Column col = field.getAnnotation(Column.class);
//				if (col != null) {
//		            System.out.println(">>>> @@@" + col.name());
//		            
//		            boolean isThereAnnot = col.name().toLowerCase().trim()
//					.equals("firSt_name".toLowerCase().trim());
//		            
//		            System.out.println(isThereAnnot);
//		            System.out.println("---------------------");
//	        
				

//			}
			
			
					
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

		}
	}


