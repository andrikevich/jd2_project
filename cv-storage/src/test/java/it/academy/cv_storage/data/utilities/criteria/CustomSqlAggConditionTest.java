package it.academy.cv_storage.data.utilities.criteria;

import it.academy.cv_storage.config.AppConfig;
import it.academy.cv_storage.data.utilities.criteria.agregation.Aggregator;
import it.academy.cv_storage.data.utilities.criteria.agregation.AvgAggregator;
import it.academy.cv_storage.data.utilities.criteria.agregation.CountAggregator;
import it.academy.cv_storage.data.utilities.helper.OrderBySortingType;
import it.academy.cv_storage.exception.*;
import it.academy.cv_storage.model.entity.Candidate;
import it.academy.cv_storage.model.utilities.Gender;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
@ContextConfiguration(classes = AppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomSqlAggConditionTest {


    @Autowired
    @Qualifier("customSqlAggCondition")
    CustomSqlAggCondition customSelector;



    //------------------ <from super class> ---------------------------


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
                .orderBy((String) null, OrderBySortingType.ASC)
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





    //------------------ <positive-tests> ---------------------------

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

    //------------------ </positive-tests> ---------------------------

    //------------------ <negative-tests> ---------------------------



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
        assertEquals("SELECT FIRST_NAME, LAST_NAME from candidate  WHERE FIRST_NAME=''".trim(), query.trim());
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
















    //------------------ <positive-tests> ---------------------------
    @Test
    public void selectFromAggWithTwoCorrectParameter() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption {
        String query = customSelector.selectFrom(Candidate.class,"first_name","lastName").getQuery();
        assertEquals("SELECT FIRST_NAME, LAST_NAME from candidate".trim(), query.trim());

    }

    @Test
    public void selectFromWithOneAggParameterAndSearchParams() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class,new AvgAggregator("birthDATE"),"first_name","lastName")
                .getQuery();
        assertEquals("SELECT AVG(BIRTH_DATE), FIRST_NAME, LAST_NAME from candidate".trim(), query.trim());

    }

    @Test
    public void selectFromWithTwoAggParameter() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class,
                List.of(new AvgAggregator("birthDATE"),new CountAggregator("lastName")),
                "first_name")
                .getQuery();
        assertEquals("SELECT AVG(BIRTH_DATE), COUNT(LAST_NAME), FIRST_NAME from candidate".trim(), query.trim());

    }

    @Test
    public void selectFromWithOneAggParameter() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class,new AvgAggregator("birthDATE"))
                .getQuery();
        assertEquals("SELECT AVG(BIRTH_DATE)  from candidate".trim(), query.trim());

    }

    @Test
    public void selectFromOneGroupByParam() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class,new AvgAggregator("birthDATE"))
                .groupBy("lastName")
                .getQuery();
        assertEquals("SELECT AVG(BIRTH_DATE)  from candidate  GROUP BY LAST_NAME".trim(), query.trim());

    }
    @Test
    public void selectFromTwoGroupByParam() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class,new AvgAggregator("birthDATE"))
                .groupBy("lastName")
                .groupBy("firstname")
                .getQuery();
        assertEquals("SELECT AVG(BIRTH_DATE)  from candidate  GROUP BY LAST_NAME , FIRST_NAME".trim(), query.trim());

    }

    @Test
    public void selectFromOneHavingOneGroupByParam() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption, FinishSqlSentenceExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class,new AvgAggregator("birthDATE"))
                .groupBy("lastName")
                .having()
                .gt("birthDate","1970-08-05")
                .getQuery();
        assertEquals("SELECT AVG(BIRTH_DATE)  from candidate  GROUP BY LAST_NAME  HAVING BIRTH_DATE>'1970-08-05'".trim(),
                    query.trim());

    }

    @Test
    public void selectFromTwoHavingOneGroupByParam() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption, FinishSqlSentenceExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class,new AvgAggregator("birthDATE"))
                .groupBy("lastName")
                .having()
                .gt("birthDate","1970-08-05")
                .and()
                .lt("birthDate","1999-08-05")
                .getQuery();
        assertEquals("SELECT AVG(BIRTH_DATE)  from candidate  GROUP BY LAST_NAME  HAVING BIRTH_DATE>'1970-08-05'  AND BIRTH_DATE<'1999-08-05'".trim(),
                query.trim());

    }

    @Test
    public void fullHouse() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption, FinishSqlSentenceExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class,new AvgAggregator("birthDATE"),"firstName")
                .groupBy("lastName")
                .having()
                .gt(new CountAggregator("firstName"),"10")
                .and()
                .lt("birthDate","1999-08-05")
                .orderBy("lastName",OrderBySortingType.DESC)
                .orderBy(new CountAggregator("firstName"), OrderBySortingType.ASC )
                .getQuery();
        assertEquals("SELECT AVG(BIRTH_DATE), FIRST_NAME from candidate  GROUP BY LAST_NAME  HAVING COUNT(FIRST_NAME)>'10'  AND BIRTH_DATE<'1999-08-05'  ORDER BY LAST_NAME DESC , COUNT(FIRST_NAME) ASC".trim(),
                query.trim());

    }



    //------------------ </positive-tests> ---------------------------

    //------------------ <negative-tests> ---------------------------





    @Test(expected = IncorrectArgumentException.class)
    public void selectFromWithEmptySearchParam() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class,new AvgAggregator("birthDATE"),"")
                .getQuery();

    }

    @Test(expected = IncorrectArgumentException.class)
    public void selectFromWithNullAgg() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class,null)
                .getQuery();

    }

    @Test(expected = IncorrectArgumentException.class)
    public void selectFromWithNullAggNullSearchParanmList() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class, (List<Aggregator>) null,null)
                .getQuery();

    }

    @Test(expected = IncorrectArgumentException.class)
    public void selectFromWithNullAggNullSearchParam() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class, (Aggregator) null,null)
                .getQuery();

    }

    // wrong order in query sentence

    @Test(expected = StartSqlSentenceExeption.class)
    public void groupByAfterHaving() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption, FinishSqlSentenceExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class,new AvgAggregator("birthDATE"),"firstName")
                .having()
                .gt(new CountAggregator("firstName"),"10")
                .and()
                .lt("birthDate","1999-08-05")
                .groupBy("lastName")
                .orderBy("lastName",OrderBySortingType.DESC)
                .orderBy(new CountAggregator("firstName"), OrderBySortingType.ASC )
                .getQuery();

    }

    @Test (expected = StartSqlSentenceExeption.class)
    public void groupByAfterOrderby() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption, FinishSqlSentenceExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class,new AvgAggregator("birthDATE"),"firstName")
                .where()
                .equal("lastName", "John")
                .having()
                .gt(new CountAggregator("firstName"),"10")
                .and()
                .lt("birthDate","1999-08-05")
                .orderBy("lastName",OrderBySortingType.DESC)
                .groupBy("lastName")
                .orderBy(new CountAggregator("firstName"), OrderBySortingType.ASC )
                .getQuery();

    }

    @Test (expected = StartSqlSentenceExeption.class)
    public void groupByBeforeWhere() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption, FinishSqlSentenceExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class,new AvgAggregator("birthDATE"),"firstName")
                .groupBy("lastName")
                .where()
                .equal("lastName", "John")
                .having()
                .gt(new CountAggregator("firstName"),"10")
                .and()
                .lt("birthDate","1999-08-05")
                .orderBy("lastName",OrderBySortingType.DESC)
                .orderBy(new CountAggregator("firstName"), OrderBySortingType.ASC )
                .getQuery();

    }

    @Test (expected = StartSqlSentenceExeption.class)
    public void havingBeforeWhere() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption, FinishSqlSentenceExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class,new AvgAggregator("birthDATE"),"firstName")
                .having()
                .gt(new CountAggregator("firstName"),"10")
                .and()
                .lt("birthDate","1999-08-05")
                .where()
                .equal("lastName", "John")
                .groupBy("lastName")
                .orderBy("lastName",OrderBySortingType.DESC)
                .orderBy(new CountAggregator("firstName"), OrderBySortingType.ASC )
                .getQuery();

    }

    @Test (expected = StartSqlSentenceExeption.class)
    public void havingBeforeGroupBy() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption, FinishSqlSentenceExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class,new AvgAggregator("birthDATE"),"firstName")
                .where()
                .equal("lastName", "John")
                .having()
                .gt(new CountAggregator("firstName"),"10")
                .and()
                .lt("birthDate","1999-08-05")
                .groupBy("lastName")
                .orderBy("lastName",OrderBySortingType.DESC)
                .orderBy(new CountAggregator("firstName"), OrderBySortingType.ASC )
                .getQuery();

    }

    @Test (expected = StartSqlSentenceExeption.class)
    public void havingAfterOrderBy() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption, FinishSqlSentenceExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class,new AvgAggregator("birthDATE"),"firstName")
                .where()
                .equal("lastName", "John")
                .groupBy("lastName")
                .orderBy("lastName",OrderBySortingType.DESC)
                .orderBy(new CountAggregator("firstName"), OrderBySortingType.ASC )
                .having()
                .gt(new CountAggregator("firstName"),"10")
                .and()
                .lt("birthDate","1999-08-05")
                .getQuery();

    }

    @Test (expected = StartSqlSentenceExeption.class)
    public void orderByBeforeWhere() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption, FinishSqlSentenceExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class,new AvgAggregator("birthDATE"),"firstName")
                .orderBy("lastName",OrderBySortingType.DESC)
                .where()
                .equal("lastName", "John")
                .groupBy("lastName")
                .having()
                .gt(new CountAggregator("firstName"),"10")
                .and()
                .lt("birthDate","1999-08-05")
                .orderBy(new CountAggregator("firstName"), OrderBySortingType.ASC )
                .getQuery();

    }

    @Test (expected = StartSqlSentenceExeption.class)
    public void orderByBeforeGroupBy() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption, FinishSqlSentenceExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class,new AvgAggregator("birthDATE"),"firstName")
                .where()
                .equal("lastName", "John")
                .orderBy("lastName",OrderBySortingType.DESC)
                .groupBy("lastName")
                .having()
                .gt(new CountAggregator("firstName"),"10")
                .and()
                .lt("birthDate","1999-08-05")
                .orderBy(new CountAggregator("firstName"), OrderBySortingType.ASC )
                .getQuery();

    }
    @Test (expected = StartSqlSentenceExeption.class)
    public void orderByBeforeHaving() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption, FinishSqlSentenceExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class,new AvgAggregator("birthDATE"),"firstName")
                .where()
                .equal("lastName", "John")
                .groupBy("lastName")
                .orderBy("lastName",OrderBySortingType.DESC)
                .having()
                .gt(new CountAggregator("firstName"),"10")
                .and()
                .lt("birthDate","1999-08-05")
                .orderBy(new CountAggregator("firstName"), OrderBySortingType.ASC )
                .getQuery();

    }

    @Test (expected = IncorrectArgumentException.class)
    public void groupByWithNullParam() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption, FinishSqlSentenceExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class,new AvgAggregator("birthDATE"),"firstName")
                .where()
                .equal("lastName", "John")
                .groupBy(null)
                .having()
                .gt(new CountAggregator("firstName"),"10")
                .and()
                .lt("birthDate","1999-08-05")
                .orderBy("lastName",OrderBySortingType.DESC)
                .orderBy(new CountAggregator("firstName"), OrderBySortingType.ASC )
                .getQuery();

    }

    @Test (expected = FinishSqlSentenceExeption.class)
    public void doubleHaving() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption, FinishSqlSentenceExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class,new AvgAggregator("birthDATE"),"firstName")
                .where()
                .equal("lastName", "John")
                .groupBy("firstName")
                .having()
                .gt(new CountAggregator("firstName"),"10")
                .and()

                .having()
                .lt("birthDate","1999-08-05")
                .orderBy("lastName",OrderBySortingType.DESC)
                .orderBy(new CountAggregator("firstName"), OrderBySortingType.ASC )
                .getQuery();

    }

    @Test (expected = IncorrectArgumentException.class)
    public void havingWithNullParam() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption, FinishSqlSentenceExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class,new AvgAggregator("birthDATE"),"firstName")
                .where()
                .equal("lastName", "John")
                .groupBy("firstName")
                .having()
                .gt((Aggregator) null,"10")
                .and()
                .lt("birthDate","1999-08-05")
                .orderBy("lastName",OrderBySortingType.DESC)
                .orderBy(new CountAggregator("firstName"), OrderBySortingType.ASC )
                .getQuery();

    }

    @Test(expected = IncorrectArgumentException.class)
    public void havingWithNullValue() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption, FinishSqlSentenceExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class,new AvgAggregator("birthDATE"),"firstName")
                .where()
                .equal("lastName", "John")
                .groupBy("firstName")
                .having()
                .gt(new CountAggregator("lastName"),null)
                .and()
                .lt("birthDate","1999-08-05")
                .orderBy("lastName",OrderBySortingType.DESC)
                .orderBy(new CountAggregator("firstName"), OrderBySortingType.ASC )
                .getQuery();

    }

    @Test(expected = IncorrectArgumentException.class)
    public void havingWithNullInAggregator() throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, IncorrectArgumentException, NullClassEntityExeption, FinishSqlSentenceExeption {
        String query = customSelector.selectWithAggregationFrom(
                Candidate.class,new AvgAggregator("birthDATE"),"firstName")
                .where()
                .equal("lastName", "John")
                .groupBy("firstName")
                .having()
                .gt(new CountAggregator(null),"10")
                .and()
                .lt("birthDate","1999-08-05")
                .orderBy("lastName",OrderBySortingType.DESC)
                .orderBy(new CountAggregator("firstName"), OrderBySortingType.ASC )
                .getQuery();

    }
}