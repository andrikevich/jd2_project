package it.academy.cv_storage.app;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import javax.persistence.Table;

import it.academy.cv_storage.data.utilities.CustomSqlSelect;
import it.academy.cv_storage.exception.ClassHasNoCorrectAnnotation;
import it.academy.cv_storage.exception.StartSqlSentenceExeption;
import it.academy.cv_storage.model.entity.Candidate;

public class Test {
	
	public static void main(String[] args)   {

		
		CustomSqlSelect selector = new CustomSqlSelect();

		
		try {
			//System.out.println(selector.selectAllFrom(Candidate.class).getQuery());
			System.out.println(selector.selectFrom(Candidate.class,"Name","Surname").getQuery());
			//System.out.println(selector.selectFrom(Candidate.class).getQuery());
		} catch (StartSqlSentenceExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassHasNoCorrectAnnotation e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		}
	}


