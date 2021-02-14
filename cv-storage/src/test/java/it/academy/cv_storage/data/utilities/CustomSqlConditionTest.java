package it.academy.cv_storage.data.utilities;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.academy.cv_storage.config.AppConfig;
import it.academy.cv_storage.exception.ClassHasNoCorrectAnnotation;
import it.academy.cv_storage.exception.IncorrectArgumentException;
import it.academy.cv_storage.exception.NullClassEntityExeption;
import it.academy.cv_storage.exception.StartSqlSentenceExeption;
import it.academy.cv_storage.model.entity.Candidate;
import it.academy.cv_storage.model.utilities.Gender;

@ContextConfiguration(classes = AppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomSqlConditionTest {
	
	@Autowired
	@Qualifier("customSqlSelect")
	CustomSqlSelect customSelector;

	@Test
	public void selectFromWhereOneEqual() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
		String query = customSelector.selectAllFrom(Candidate.class)
				 					 .where()
				 					 .equal("firstName", "John")
									 .getQuery();
		assertEquals("SELECT * from candidate  WHERE FIRST_NAME='John'".trim(), query.trim());
	}
	
	@Test
	public void selectFromWhereFirstEqualSecongGt() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
		String query = customSelector.selectAllFrom(Candidate.class)
				 					 .where()
				 					 .equal("firstName", "John")
				 					 .and()
				 					 .gt("birthDate", "1980-02-02")
									 .getQuery();
		assertEquals("SELECT * from candidate  WHERE FIRST_NAME='John'  AND BIRTH_DATE>'1980-02-02'".trim(), query.trim());
	}
	
	@Test
	public void selectFromWhereThreeConditions() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
		String query = customSelector.selectAllFrom(Candidate.class)
				 					 .where()
				 					 .equal("firstName", "John")
				 					 .and()
				 					 .gt("birthDate", "1980-02-02")
				 					 .or()
				 					 .notEqual("gender", Gender.МУЖЧИНА.toString())
									 .getQuery();
		assertEquals("SELECT * from candidate  WHERE FIRST_NAME='John'  AND BIRTH_DATE>'1980-02-02'  OR GENDER<>'МУЖЧИНА'".trim(), query.trim());
	}

	@Test
	public void selectFromWhereOneEqualOrderBy() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
		String query = customSelector.selectAllFrom(Candidate.class)
				 					 .where()
				 					 .equal("firstName", "John")
				 					 .orderBy("firstName", OrderBySortingType.DESC)
									 .getQuery();
		assertEquals("SELECT * from candidate  WHERE FIRST_NAME='John'  ORDER BY FIRST_NAME DESC".trim(), query.trim());
	}
}
