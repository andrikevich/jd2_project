package it.academy.cv_storage.data.utilities.criteria;

import static org.junit.Assert.*;

import it.academy.cv_storage.data.utilities.criteria.CustomSqlCondition;
import it.academy.cv_storage.data.utilities.helper.OrderBySortingType;
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
	@Qualifier("customSqlCondition")
	CustomSqlCondition customSelector;


	@Test
	public void selectFromWithTwoCorrectParameter() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption {
		String query = customSelector.selectFrom(Candidate.class,"first_name","lastName").getQuery();
		assertEquals("SELECT FIRST_NAME, LAST_NAME from candidate".trim(), query.trim());

	}


	
	//------------------ <positive-tests> ---------------------------

	@Test
	public void selectFromWhereOneEqual() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
		String query = customSelector.selectAllFrom(Candidate.class)
				 					 .where()
				 					 .equal("firstName", "John")
									 .getQuery();
		assertEquals("SELECT * from candidate  WHERE  FIRST_NAME='John'".trim(), query.trim());
	}
	
	@Test
	public void selectFromWhereFirstEqualSecongGt() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
		String query = customSelector.selectAllFrom(Candidate.class)
				 					 .where()
				 					 .equal("firstName", "John")
				 					 .and()
				 					 .gt("birthDate", "1980-02-02")
									 .getQuery();
		assertEquals("SELECT * from candidate  WHERE  FIRST_NAME='John' AND  BIRTH_DATE>'1980-02-02'".trim(), query.trim());
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
		assertEquals("SELECT * from candidate  WHERE  FIRST_NAME='John' AND  BIRTH_DATE>'1980-02-02' OR  GENDER<>'МУЖЧИНА'".trim(), query.trim());
	}

	@Test
	public void selectFromWhereOneEqualOrderBy() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
		String query = customSelector.selectAllFrom(Candidate.class)
				 					 .where()
				 					 .equal("firstName", "John")
				 					 .orderBy("firstName", OrderBySortingType.DESC)
									 .getQuery();
		assertEquals("SELECT * from candidate  WHERE  FIRST_NAME='John' ORDER BY FIRST_NAME DESC".trim(), query.trim());
	}
	
	//------------------ </positive-tests> ---------------------------
	
	//------------------ <negative-tests> ---------------------------
	
	@Test(expected = StartSqlSentenceExeption.class)
	public void invokeWhereAfterOrderBy() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
	 	customSelector.selectFrom(Candidate.class,"fIRst_name","lastname")	
					  .orderBy("firstName", OrderBySortingType.ASC)
					  .where()
					  .getQuery();
	}
	
	@Test(expected = StartSqlSentenceExeption.class)
	public void invokeWhereWithoutConditions() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
	 	customSelector.selectFrom(Candidate.class,"fIRst_name","lastname")
					  .where()
					  .getQuery();
	}
	
	
	@Test(expected = IncorrectArgumentException.class)
	public void invokeWhereWithNullParamNameConditions() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
	 	customSelector.selectFrom(Candidate.class,"fIRst_name","lastname")
					  .where()
					  .equal(null, "John")
					  .getQuery();
	}
	@Test(expected = IncorrectArgumentException.class)
	public void invokeWhereWithNullValueOfParamConditions() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
	 	customSelector.selectFrom(Candidate.class,"fIRst_name","lastname")
					  .where()
					  .equal("firstName", null)
					  .getQuery();
	}
	
	@Test
	public void invokeWhereWithEmptyValueOfParamConditions() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
		String query = customSelector.selectFrom(Candidate.class,"fIRst_name","lastname")
									  .where()
									  .equal("firstName", "")
									  .getQuery();
		assertEquals("SELECT FIRST_NAME, LAST_NAME from candidate  WHERE  FIRST_NAME=''".trim(), query.trim());
	}
	
	@Test(expected = IncorrectArgumentException.class)
	public void invokeWhereWithEmptyParamName() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
	 	customSelector.selectFrom(Candidate.class,"fIRst_name","lastname")
					  .where()
					  .equal("", "John")
					  .getQuery();
	}
	
	@Test(expected = StartSqlSentenceExeption.class)
	public void invokeWhereWithDoubleConditionsWithoutConcat() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
	 	customSelector.selectFrom(Candidate.class,"fIRst_name","lastname")
					  .where()
					  .equal("firstName", "John")
					  .equal("lastName", "Carter")
					  .getQuery();
	}
	
	@Test(expected = IncorrectArgumentException.class)
	public void invokeDoubleWhereWithCondit() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
	 	customSelector.selectFrom(Candidate.class,"fIRst_name","lastname")
					  .where()
					  .equal("firstName", null)
					  .where()
					  .getQuery();
	}
	
	@Test(expected = StartSqlSentenceExeption.class)
	public void invokeDoubleWhere() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
	 	customSelector.selectFrom(Candidate.class,"fIRst_name","lastname")
					  .where()
					  .where()
					  .getQuery();
	}
	
	@Test(expected = StartSqlSentenceExeption.class)
	public void invokeConcatWithoutCondition() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
	 	customSelector.selectFrom(Candidate.class,"fIRst_name","lastname")
					  .where()
					  .and()
					  .getQuery();
	}
	
	@Test(expected = StartSqlSentenceExeption.class)
	public void invokeOrderByAfterWhere() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
	 	customSelector.selectFrom(Candidate.class,"fIRst_name","lastname")
					  .where()
					  .orderBy("lastName", OrderBySortingType.ASC)
					  .getQuery();
	}
	
	@Test(expected = StartSqlSentenceExeption.class)
	public void invokeOrderByAfterConcat() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
	 	customSelector.selectFrom(Candidate.class,"fIRst_name","lastname")
					  .where()
					  .equal("firstName", "John")
					  .and()
					  .orderBy("lastName", OrderBySortingType.ASC)
					  .getQuery();
	}
	
}
