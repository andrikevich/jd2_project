package it.academy.cv_storage.data.utilities;

import it.academy.cv_storage.data.utilities.agregation.Aggregator;
import it.academy.cv_storage.exception.ClassHasNoCorrectAnnotation;
import it.academy.cv_storage.exception.IncorrectArgumentException;
import it.academy.cv_storage.exception.NullClassEntityExeption;
import it.academy.cv_storage.exception.StartSqlSentenceExeption;

public class CustomSqlAggCondition extends CustomSqlCondition{
    public CustomSqlAggCondition(StringBuilder startQuery) {
        this.startQuery = startQuery;
    }

    // ------------- conditions ------------------

    public CustomSqlAggCondition equal(Aggregator aggPar, String value) throws NoSuchFieldException, SecurityException,
            StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
        return conditionCreate(aggPar, value, "=");

    }

    public CustomSqlAggCondition notEqual(Aggregator aggPar, String value) throws NoSuchFieldException, SecurityException,
            StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
        return conditionCreate(aggPar, value, "<>");

    }

    public CustomSqlAggCondition lt(Aggregator aggPar, String value) throws NoSuchFieldException, SecurityException,
            StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
        return conditionCreate(aggPar, value, "<");

    }

    public CustomSqlAggCondition le(Aggregator aggPar, String value) throws NoSuchFieldException, SecurityException,
            StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
        return conditionCreate(aggPar, value, "<=");

    }

    public CustomSqlAggCondition gt(Aggregator aggPar, String value) throws NoSuchFieldException, SecurityException,
            StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
        return conditionCreate(aggPar, value, ">");

    }

    public CustomSqlAggCondition ge(Aggregator aggPar, String value) throws NoSuchFieldException, SecurityException,
            StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
        return conditionCreate(aggPar, value, ">=");

    }

    public CustomSqlAggCondition like(Aggregator aggPar, String wildcard) throws NoSuchFieldException, SecurityException,
            StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
        return conditionCreate(aggPar, wildcard, " LIKE ");

    }




    private CustomSqlAggCondition conditionCreate(Aggregator aggPar, String value, String operator)
            throws ClassHasNoCorrectAnnotation, NullClassEntityExeption, NoSuchFieldException, StartSqlSentenceExeption,
            IncorrectArgumentException {
        if (!conditionStarted) {
            if(value == null) {
                throw new IncorrectArgumentException("It was inserted null instead of value of parameter");
            }

            ClassInfoRetriever classInfo = new ClassInfoRetriever(clsFrom);
            String correctParamName = classInfo.getSelectParameter(aggPar.getParamName());

            startQuery.append(aggPar.getAggrFunc())
                    .append("(")
                    .append(correctParamName)
                    .append(")")
                    .append(operator.toUpperCase())
                    .append("'")
                    .append(value)
                    .append("' ");
            conditionStarted = true;
            return this;
        } else
            throw new StartSqlSentenceExeption(
                    "Previous conditional method was invoked. You can't invoke it again. You only can invoke concatenation conditions (or,  and, etc.)");
    }

    @Override
    public CustomSqlAggCondition equal(String paramName, String value) throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
        return (CustomSqlAggCondition)super.equal(paramName, value);
    }

    @Override
    public CustomSqlAggCondition notEqual(String paramName, String value) throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
        return (CustomSqlAggCondition)super.notEqual(paramName, value);
    }

    @Override
    public CustomSqlAggCondition lt(String paramName, String value) throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
        return this.lt(paramName, value);
    }

    @Override
    public CustomSqlAggCondition le(String paramName, String value) throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
        return (CustomSqlAggCondition)super.le(paramName, value);
    }

    @Override
    public CustomSqlAggCondition gt(String paramName, String value) throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
        return (CustomSqlAggCondition)super.gt(paramName, value);
    }

    @Override
    public CustomSqlAggCondition ge(String paramName, String value) throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
        return (CustomSqlAggCondition)super.ge(paramName, value);
    }

    @Override
    public CustomSqlAggCondition like(String paramName, String wildcard) throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
        return (CustomSqlAggCondition)super.like(paramName, wildcard);
    }

    @Override
    public CustomSqlAggCondition or() throws StartSqlSentenceExeption {
        return (CustomSqlAggCondition)super.or();
    }

    @Override
    public CustomSqlAggCondition and() throws StartSqlSentenceExeption {
        return (CustomSqlAggCondition)super.and();
    }
}
