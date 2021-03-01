package it.academy.cv_storage.data.utilities.criteria;

import it.academy.cv_storage.data.utilities.helper.ClassInfoRetriever;
import it.academy.cv_storage.data.utilities.criteria.agregation.Aggregator;
import it.academy.cv_storage.exception.ClassHasNoCorrectAnnotation;
import it.academy.cv_storage.exception.IncorrectArgumentException;
import it.academy.cv_storage.exception.NullClassEntityExeption;
import it.academy.cv_storage.exception.StartSqlSentenceExeption;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@NoArgsConstructor
public class CustomSqlAggCondition extends CustomSqlCondition{
    protected boolean conditionHavingStarted = false;

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
        if (!conditionHavingStarted) {
            if(value == null) {
                throw new IncorrectArgumentException("It was inserted null instead of value of parameter");
            }
            if(aggPar == null) {
                throw new IncorrectArgumentException("It was inserted null instead of Aggregator");
            }

            ClassInfoRetriever classInfo = new ClassInfoRetriever(clsFrom);
            String correctParamName = classInfo.getSelectParameter(aggPar.getParamName());
            StringBuilder paramSb = new StringBuilder();
            paramSb.append(aggPar.getAggrFunc())
                    .append("(")
                    .append(correctParamName)
                    .append(")")
                    .append(operator.toUpperCase())
                    .append("'")
                    .append(value)
                    .append("'");
            startQuery.append(" ").append(paramSb);
            conditionHavingStarted = true;
            paramOfQuery.add(paramSb.toString());
            return this;
        } else
            throw new StartSqlSentenceExeption(
                    "Previous conditional method was invoked. You can't invoke it again. You only can invoke concatenation conditions (or,  and, etc.)");
    }

    @Override
    public CustomSqlAggCondition equal(String paramName, String value) throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
        conditionHavingStarted = true;
        return (CustomSqlAggCondition)super.equal(paramName, value);
    }

    @Override
    public CustomSqlAggCondition notEqual(String paramName, String value) throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
        conditionHavingStarted = true;
        return (CustomSqlAggCondition)super.notEqual(paramName, value);
    }

    @Override
    public CustomSqlAggCondition lt(String paramName, String value) throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
        conditionHavingStarted = true;
        return (CustomSqlAggCondition)super.lt(paramName, value);
    }

    @Override
    public CustomSqlAggCondition le(String paramName, String value) throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
        conditionHavingStarted = true;
        return (CustomSqlAggCondition)super.le(paramName, value);
    }

    @Override
    public CustomSqlAggCondition gt(String paramName, String value) throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
        conditionHavingStarted = true;
        return (CustomSqlAggCondition)super.gt(paramName, value);
    }

    @Override
    public CustomSqlAggCondition ge(String paramName, String value) throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
        conditionHavingStarted = true;
        return (CustomSqlAggCondition)super.ge(paramName, value);
    }

    @Override
    public CustomSqlAggCondition like(String paramName, String wildcard) throws NoSuchFieldException, SecurityException, StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
        conditionHavingStarted = true;
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

    @Override
    public String getQuery() throws StartSqlSentenceExeption {
        if(isSelectPresent == false) {
            throw new StartSqlSentenceExeption(
                    "The beginning of SQL query is incorrect. You should use selectAllFrom() or selectFrom() or selectWithAggregationFrom() first");

        }else if (isHavingPresent == false && isWherePresent == false) {
                    return startQuery.toString();
                }else if (isWherePresent == true && isHavingPresent == false){
                        if (conditionWhereStarted ) {
                            return startQuery.toString();
                        } else
                            throw new StartSqlSentenceExeption("You should finish you query with some condition near WHERE");
                    } else if (isWherePresent == false && isHavingPresent == true){
                                if (conditionHavingStarted) {
                                    return startQuery.toString();
                                } else
                                    throw new StartSqlSentenceExeption("You should finish you query with some condition near HAVING");
                            } else if (isWherePresent == true && isHavingPresent == true){
                                        if(!conditionWhereStarted)
                                            throw new StartSqlSentenceExeption("You should finish you query with some condition near WHERE");
                                        else if (!isHavingPresent)
                                                throw new StartSqlSentenceExeption("You should finish you query with some condition near HAVING");
                                                else return startQuery.toString();
                                  }


        return startQuery.toString();
    }


    @Override
    public <T> CustomSqlSelect selectAllFrom(Class<T> clsFrom) throws StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
        return super.selectAllFrom(clsFrom);
    }

    @Override
    protected CustomSqlCondition concatenate(String concatinator) throws StartSqlSentenceExeption {

        if (conditionWhereStarted) {
            startQuery.append(" ").append(concatinator.toUpperCase()).append(" ");
            conditionWhereStarted = false;
            return this;
        } else if(conditionHavingStarted){
            startQuery.append(" ").append(concatinator.toUpperCase()).append(" ");
            conditionHavingStarted = false;
            return this;
        } else
            throw new StartSqlSentenceExeption(concatinator
                    + "() can be applied ONLY after conditional operation e.g. (equal, notEqual, gt etc.)");
    }
}
