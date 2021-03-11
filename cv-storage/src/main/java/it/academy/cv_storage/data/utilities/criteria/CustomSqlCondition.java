package it.academy.cv_storage.data.utilities.criteria;

import it.academy.cv_storage.data.utilities.helper.ClassInfoRetriever;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import it.academy.cv_storage.exception.ClassHasNoCorrectAnnotation;
import it.academy.cv_storage.exception.IncorrectArgumentException;
import it.academy.cv_storage.exception.NullClassEntityExeption;
import it.academy.cv_storage.exception.StartSqlSentenceExeption;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
@NoArgsConstructor
public class CustomSqlCondition extends CustomSqlSelect {

	protected boolean conditionWhereStarted = false;

	public CustomSqlCondition(StringBuilder startQuery) {
		this.startQuery = startQuery;
	}




	// ------------- conditions ------------------

	// EQUAL String "parameter" = String "value"
	public CustomSqlCondition equal(String paramName, String value) throws NoSuchFieldException, SecurityException,
			StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
		return conditionCreate(paramName, value, "=");

	}

	public CustomSqlCondition notEqual(String paramName, String value) throws NoSuchFieldException, SecurityException,
			StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
		return conditionCreate(paramName, value, "<>");

	}

	public CustomSqlCondition lt(String paramName, String value) throws NoSuchFieldException, SecurityException,
			StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
		return conditionCreate(paramName, value, "<");

	}

	public CustomSqlCondition le(String paramName, String value) throws NoSuchFieldException, SecurityException,
			StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
		return conditionCreate(paramName, value, "<=");

	}

	public CustomSqlCondition gt(String paramName, String value) throws NoSuchFieldException, SecurityException,
			StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
		return conditionCreate(paramName, value, ">");

	}

	public CustomSqlCondition ge(String paramName, String value) throws NoSuchFieldException, SecurityException,
			StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
		return conditionCreate(paramName, value, ">=");

	}

	public CustomSqlCondition like(String paramName, String wildcard) throws NoSuchFieldException, SecurityException,
	StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
return conditionCreate(paramName, wildcard, " LIKE ");

}
	// ------------- concatenators ------------------

	public CustomSqlCondition or() throws StartSqlSentenceExeption {
		return concatenate("OR");
	}
	
	public CustomSqlCondition and() throws StartSqlSentenceExeption {
		return concatenate("AND");
	}
	


	@Override
	public String getQuery() throws StartSqlSentenceExeption {
		if(isSelectPresent == false) {
			throw new StartSqlSentenceExeption(
					"The beginning of SQL query is incorrect. You should use selectAllFrom() or selectFrom() or selectWithAggregationFrom() first");
		}else if(isWherePresent) {
			if (conditionWhereStarted) {
				return startQuery.toString();
			} else
				throw new StartSqlSentenceExeption("You should finish you query with some condition near WHERE");
		}
		return startQuery.toString();
	}
	
	//----------- helper methods ------------------

	private CustomSqlCondition conditionCreate(String paramName, String value, String operator)
			throws ClassHasNoCorrectAnnotation, NullClassEntityExeption, NoSuchFieldException, StartSqlSentenceExeption,
			IncorrectArgumentException {
		if (!conditionWhereStarted) {
			if(value == null) {
				throw new IncorrectArgumentException("It was inserted null instead of value of parameter");
			}
			
			ClassInfoRetriever classInfo = new ClassInfoRetriever(clsFrom);
			String correctParamName = classInfo.getSelectParameter(clsFrom,paramName);
			StringBuilder paramSb = new StringBuilder();
			paramSb.append(correctParamName)
					.append(operator.toUpperCase())
					.append("'")
					.append(value)
					.append("'");
			startQuery.append(" ").append(paramSb);
			conditionWhereStarted = true;
			paramOfQuery.add(paramSb.toString());
			return this;
		} else
			throw new StartSqlSentenceExeption(
					"Previous conditional method was invoked. You can't invoke it again. You only can invoke concatenation conditions (or,  and, etc.)");
	}

	protected CustomSqlCondition concatenate(String concatinator) throws StartSqlSentenceExeption {
		if (conditionWhereStarted) {
			startQuery.append(" ").append(concatinator.toUpperCase()).append(" ");
			conditionWhereStarted = false;
			return this;
		} else
			throw new StartSqlSentenceExeption(concatinator
					+ "() can be applied ONLY after conditional operation e.g. (equal, notEqual, gt etc.)");
	}


}
