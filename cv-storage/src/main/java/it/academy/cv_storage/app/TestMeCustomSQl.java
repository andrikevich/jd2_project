package it.academy.cv_storage.app;


import java.util.List;
import java.util.logging.Logger;

import it.academy.cv_storage.data.utilities.criteria.agregation.MaxAggregator;
import it.academy.cv_storage.exception.*;
import org.springframework.beans.factory.annotation.Autowired;

import it.academy.cv_storage.data.dao.CvDaoImpl;
import it.academy.cv_storage.data.utilities.criteria.CustomSqlSelect;
import it.academy.cv_storage.data.utilities.helper.OrderBySortingType;
import it.academy.cv_storage.model.entity.Candidate;

public class TestMeCustomSQl {
	
	@Autowired
	static 	CvDaoImpl dao;
	
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
										.equal("firstName", "Иван")
										.or()
										.gt("lastName", "Иванов")
										.orderBy("firstName", OrderBySortingType.ASC)
										.getQuery();
			System.out.println(query3);

			// ----------------4----------------------
			CustomSqlSelect customSql4 = new CustomSqlSelect();
			String query4 = customSql4.selectWithAggregationFrom(Candidate.class,
											List.of( new MaxAggregator("firstName")),
											"firstName","middle_name")
										.where()
										.equal("firstName", "John")
										.or()
										.gt("lastName", "Brown")
					                    .groupBy("firstName")
										.having()
												.equal(new MaxAggregator("middleName"),"yyyyyy")
												.and()
												.gt("birthDate","1970-02-03" )
										.orderBy("firstName", OrderBySortingType.ASC)
										.getQuery();
			System.out.println(query4);


			System.out.println(customSql4.getParamOfQuery());


			
		} catch (StartSqlSentenceExeption | ClassHasNoCorrectAnnotation | NoSuchFieldException | SecurityException | IncorrectArgumentException | NullClassEntityExeption | FinishSqlSentenceExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}

