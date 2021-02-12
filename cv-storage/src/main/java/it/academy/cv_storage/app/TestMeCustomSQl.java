package it.academy.cv_storage.app;


import it.academy.cv_storage.data.utilities.CustomSqlSelect;
import it.academy.cv_storage.exception.ClassHasNoCorrectAnnotation;
import it.academy.cv_storage.exception.IncorrectArgumentException;
import it.academy.cv_storage.exception.StartSqlSentenceExeption;
import it.academy.cv_storage.model.entity.Candidate;

public class TestMeCustomSQl {
	
	public static void main(String[] args) {
		
	
		try {
			// ----------------1----------------------
			CustomSqlSelect customSql1 = new CustomSqlSelect();
			String query1 = customSql1.selectAllFrom(Candidate.class).getQuery();
			System.out.println(query1);
			
			// ----------------2----------------------
			CustomSqlSelect customSql2 = new CustomSqlSelect();
			String query2 = customSql2.selectFrom
					(Candidate.class,"firstName","middle_name").getQuery();
			System.out.println(query2);
			
			
		} catch (StartSqlSentenceExeption | ClassHasNoCorrectAnnotation | NoSuchFieldException | SecurityException | IncorrectArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
