package it.academy.cv_storage.service;

import static org.junit.Assert.*;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.hibernate.query.Query;
import it.academy.cv_storage.config.AppConfig;
import it.academy.cv_storage.data.utilities.CustomSqlCondition;
import it.academy.cv_storage.exception.ClassHasNoCorrectAnnotation;
import it.academy.cv_storage.exception.IncorrectArgumentException;
import it.academy.cv_storage.exception.NullClassEntityExeption;
import it.academy.cv_storage.exception.StartSqlSentenceExeption;
import it.academy.cv_storage.model.entity.Candidate;
import it.academy.cv_storage.model.utilities.Gender;

import static org.junit.Assert.*;

@ContextConfiguration(classes = AppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CvServiceTest {

	@Autowired
	CvService service;
	
	
	@Autowired
	CustomSqlCondition sqlCreator;
	
	@Test
	@Transactional
	public void test1MorskayaMariya() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
		String sqlQuery = sqlCreator.selectAllFrom(Candidate.class)
								    .where()
								    .equal("lastName", "Морская")
								    .and()
								    .equal("firstName", "Мария")
								    .and()
								    .equal("middleName", "Васильевна")
								    .getQuery();
		System.out.println(">>> SQL Query: " + sqlQuery);
		List<Candidate> candidates = service.getCandidateBySql(sqlQuery);
		System.out.println(">>> Result of Query: " + candidates.get(0));
		assertNotNull(candidates);
		assertEquals("c0ad2f85-6920-11eb-8bfa-a08cfda726f3",candidates.get(0).getId());
	}
	
	@Test
	@Transactional
	public void test2LastNameFinishOvOrWoman() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
		String sqlQuery = sqlCreator.selectAllFrom(Candidate.class)
								    .where()
								    .like("lastName", "%ов")
								    .or()
								    .equal("gender", Gender.ЖЕНЩИНА.toString())
								    .getQuery();
		System.out.println(">>> SQL Query: " + sqlQuery);
		List<Candidate> candidates = service.getCandidateBySql(sqlQuery);
		System.out.println(">>> Result of Query: " + candidates);
		assertNotNull(candidates);
		assertEquals(3,candidates.size());
	}


}
