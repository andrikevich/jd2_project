package it.academy.cv_storage.data.utilities;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.academy.cv_storage.config.AppConfig;
import it.academy.cv_storage.exception.ClassHasNoCorrectAnnotation;
import it.academy.cv_storage.exception.IncorrectArgumentException;
import it.academy.cv_storage.exception.StartSqlSentenceExeption;
import it.academy.cv_storage.model.entity.Candidate;

@ContextConfiguration(classes = AppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomSqlSelectTest {

	@Autowired
	CustomSqlSelect customSelector;
	
	
	
	@Test
	public void selectAllTest() throws StartSqlSentenceExeption, ClassHasNoCorrectAnnotation  {
		String query = customSelector.selectAllFrom(Candidate.class).getQuery();
		assertEquals("SELECT * from candidate".trim(), query.trim());
	
	}
	
	
	
	@Test
	public void selectFromWithOneCorrectParameter() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException  {
		String query = customSelector.selectFrom(Candidate.class,"first_name").getQuery();
		assertEquals("SELECT  FIRST_NAME from candidate".trim(), query.trim());
	
	}

}
