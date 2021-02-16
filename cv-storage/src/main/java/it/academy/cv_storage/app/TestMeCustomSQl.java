package it.academy.cv_storage.app;


import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import it.academy.cv_storage.dao.CvDaoImpl;
import it.academy.cv_storage.data.utilities.CustomSqlSelect;
import it.academy.cv_storage.data.utilities.OrderBySortingType;
import it.academy.cv_storage.exception.ClassHasNoCorrectAnnotation;
import it.academy.cv_storage.exception.IncorrectArgumentException;
import it.academy.cv_storage.exception.NullClassEntityExeption;
import it.academy.cv_storage.exception.StartSqlSentenceExeption;
import it.academy.cv_storage.model.entity.Candidate;

public class TestMeCustomSQl {
	
	@Autowired
	static
	CvDaoImpl dao;
	
	private static Logger logger = Logger.getLogger(TestMeCustomSQl.class.getName());
	
	public static void main(String[] args) {
		
	
		try {
			// ----------------1----------------------
			CustomSqlSelect customSql1 = new CustomSqlSelect();
			String query1 = customSql1.selectAllFrom(Candidate.class)
										.where()
										.equal("firstName", "Мария")
										.and()
										.equal("lastName", "Морская")
										.getQuery();
			System.out.println(query1);
//			
//			// ----------------2----------------------
			CustomSqlSelect customSql2 = new CustomSqlSelect();
			String query2 = customSql2.selectFrom
					(Candidate.class,"firstName","middle_name")
					.orderBy("firstName", OrderBySortingType.ASC)
					.getQuery();
			System.out.println(query2);
//			
			// ----------------3----------------------
			CustomSqlSelect customSql3 = new CustomSqlSelect();
			String query3 = customSql3.selectFrom(Candidate.class,"firstName","middle_name")
										.where()
										.equal("firstName", "John")
										.or()
										.gt("lastName", "Brown")
										.orderBy("firstName", OrderBySortingType.ASC)
										.getQuery();
			System.out.println(query3);
			
			
				System.out.println(dao.getAllCandidateBySql(query1));
			
			
			
		} catch (StartSqlSentenceExeption | ClassHasNoCorrectAnnotation | NoSuchFieldException | SecurityException | IncorrectArgumentException | NullClassEntityExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
