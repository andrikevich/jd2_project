package it.academy.cv_storage.service;

import static org.junit.Assert.*;

import java.util.List;
import java.util.logging.Logger;

import it.academy.cv_storage.data.dao.CvDaoImpl;
import it.academy.cv_storage.exception.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import it.academy.cv_storage.config.AppConfig;
import it.academy.cv_storage.data.utilities.criteria.CustomSqlCondition;
import it.academy.cv_storage.model.entity.Candidate;
import it.academy.cv_storage.model.utilities.Gender;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(classes = AppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CvServiceTest {

	@Autowired
	CvService service;

	Logger logger = Logger.getLogger(getClass().getName());

	@Autowired
	@Qualifier("customSqlCondition")
	CustomSqlCondition sqlCreator;


	@Test
	@Transactional
	public void test1MorskayaMariya() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption, FinishSqlSentenceExeption {
		String sqlQuery = sqlCreator.selectAllFrom(Candidate.class)
				.where()
				.equal("lastName", "Морская")
				.and()
				.equal("firstName", "Мария")
				.and()
				.equal("middleName", "Васильевна")
				.getQuery();
		logger.info(">>> SQL Query: " + sqlQuery);
		List<Candidate> candidates = service.getCandidateBySql(sqlQuery);
		logger.info(">>> Result of Query: " + candidates.get(0));
		logger.info("\n\n>>>> Search parameters: " + sqlCreator.getParamOfQuery() + "\n");
		assertNotNull(candidates);
		assertEquals("c0ad2f85-6920-11eb-8bfa-a08cfda726f3", candidates.get(0).getId());
	}

	@Test
	@Transactional
	public void test2LastNameFinishOvOrWoman() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption, FinishSqlSentenceExeption {
		String sqlQuery = sqlCreator.selectAllFrom(Candidate.class)
				.where()
				.like("lastName", "%ов")
				.or()
				.equal("gender", Gender.ЖЕНЩИНА.toString())
				.getQuery();
		logger.info(">>> SQL Query: " + sqlQuery);
		List<Candidate> candidates = service.getCandidateBySql(sqlQuery);
		logger.info(">>> Result of Query: " + candidates);
		logger.info("\n\n>>>> Search parameters: " + sqlCreator.getParamOfQuery() + "\n");
		assertNotNull(candidates);
		assertEquals(9, candidates.size());
	}

	@Test
	@Transactional
	public void test3() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption, FinishSqlSentenceExeption {
		String sqlQuery = sqlCreator.selectAllFrom(Candidate.class)
				.where()
				.like("lastName", "%ов")
				.and()
				.equal("EmAIL_address", "petrovich@gmail.com")
				.and()
				.equal("knowledgeName", "Git")
				.getQuery();
		logger.info(">>> SQL Query: " + sqlQuery);
		List<Candidate> candidates = service.getCandidateBySql(sqlQuery);
		logger.info(">>> Result of Query: " + candidates);
		logger.info("\n\n>>>> Search parameters: " + sqlCreator.getParamOfQuery() + "\n");


	}
}
