package it.academy.cv_storage.data.utilities;

import static org.junit.Assert.assertEquals;
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

@ContextConfiguration(classes = AppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomSqlSelectTest {

	@Autowired
	@Qualifier("customSqlSelect")
	CustomSqlSelect customSelector;
	
	// -------------  selectAllFrom -----------------
	
	@Test
	public void selectAllTest() throws StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, NullClassEntityExeption  {
		String query = customSelector.selectAllFrom(Candidate.class).getQuery();
		assertEquals("SELECT * from candidate".trim(), query.trim());
	
	}
	
	
	@Test(expected = ClassHasNoCorrectAnnotation.class)
	public void selectAllFromWrongClassTest() throws StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, NullClassEntityExeption  {
		customSelector.selectAllFrom(String.class).getQuery();
	
	}
	
	@Test(expected = NullClassEntityExeption.class)
	public void selectAllFromNullTest() throws StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, NullClassEntityExeption  {
		customSelector.selectAllFrom(null).getQuery();
	
	}
	
	
	// -------------  selectFrom -----------------	
	
	@Test (expected = ClassHasNoCorrectAnnotation.class)
	public void selectFromWithIncorrectClass() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
		customSelector.selectFrom(String.class,"first_name").getQuery();
	
	}
	
	@Test
	public void selectFromWithOneCorrectParameter() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
		String query = customSelector.selectFrom(Candidate.class,"first_name").getQuery();
		assertEquals("SELECT FIRST_NAME from candidate".trim(), query.trim());
	
	}
	
	@Test (expected = IncorrectArgumentException.class)
	public void selectFromWithOneNullParameter() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
		customSelector.selectFrom(Candidate.class,null).getQuery();
	}
	
	@Test(expected = IncorrectArgumentException.class)
	public void selectFromWithOneEmptyParameter() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
		customSelector.selectFrom(Candidate.class,"").getQuery();
	}
	
	@Test
	public void selectFromWithTwoCorrectParameter() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
		String query = customSelector.selectFrom(Candidate.class,"first_name","lastName").getQuery();
		assertEquals("SELECT FIRST_NAME, LAST_NAME from candidate".trim(), query.trim());
	
	}
	@Test
	public void selectFromWithTwoCorrectParameterWithoutCase() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
		String query = customSelector.selectFrom(Candidate.class,"fIRst_name","lastname").getQuery();
		assertEquals("SELECT FIRST_NAME, LAST_NAME from candidate".trim(), query.trim());
	}
	
	@Test(expected = IncorrectArgumentException.class)
	public void selectFromWithTwoParameterSecondNull() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
		customSelector.selectFrom(Candidate.class,"first_name",null).getQuery();
	}
	
	@Test(expected = IncorrectArgumentException.class)
	public void selectFromWithTwoParameterSecondEmpty() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
		customSelector.selectFrom(Candidate.class,"first_name","").getQuery();
	}
	
	@Test(expected = IncorrectArgumentException.class)
	public void selectFromWithTwoParameterSecondIncorrect() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
		customSelector.selectFrom(Candidate.class,"first_name","987").getQuery();
	}
	
	// -------------  where -----------------	
	@Test(expected = StartSqlSentenceExeption.class)
	public void invokeWhereWithoutSelect() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
		customSelector.where();
	}
	
	@Test(expected = StartSqlSentenceExeption.class)
	public void invokeWhereAfterOrderBy() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
	 	customSelector.selectFrom(Candidate.class,"fIRst_name","lastname")	
					  .orderBy("firstName", OrderBySortingType.ASC)
					  .where()
					  .getQuery();
	}
	
	// -------------  order by -----------------	
	@Test
	public void invokeOrderByOneArg() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
		String query = 	customSelector.selectFrom(Candidate.class,"fIRst_name","lastname")	
					  				  .orderBy("firstName", OrderBySortingType.ASC)
					  				  .getQuery();
		assertEquals("SELECT FIRST_NAME, LAST_NAME from candidate  ORDER BY FIRST_NAME ASC".trim(), query.trim());
	}
	
	@Test
	public void invokeOrderByTwoArg() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
		String query = 	customSelector.selectFrom(Candidate.class,"fIRst_name","lastname")	
					  				  .orderBy("firstName", OrderBySortingType.ASC)
					  				  .orderBy("lastName", OrderBySortingType.ASC)
					  				  .getQuery();
		assertEquals("SELECT FIRST_NAME, LAST_NAME from candidate  ORDER BY FIRST_NAME ASC , LAST_NAME ASC".trim(), query.trim());
	}
	
	@Test(expected = IncorrectArgumentException.class)
	public void invokeOrderByOneNullArg() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
		customSelector.selectFrom(Candidate.class,"fIRst_name","lastname")	
					  .orderBy(null, OrderBySortingType.ASC)
					  .getQuery();
	}
	
	@Test(expected = IncorrectArgumentException.class)
	public void invokeOrderByOneEmptyArg() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
		customSelector.selectFrom(Candidate.class,"fIRst_name","lastname")	
					  .orderBy("", OrderBySortingType.ASC)
					  .getQuery();
	}
	
	@Test(expected = IncorrectArgumentException.class)
	public void invokeOrderByOneNullSortingType() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
		customSelector.selectFrom(Candidate.class,"fIRst_name","lastname")	
					  .orderBy("lastName", null)
					  .getQuery();
	}
	
	@Test(expected = StartSqlSentenceExeption.class)
	public void invokeOrderByWithoutSelect() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption  {
	 	customSelector.orderBy("firstName", OrderBySortingType.ASC)
					  .getQuery();
	}
}
