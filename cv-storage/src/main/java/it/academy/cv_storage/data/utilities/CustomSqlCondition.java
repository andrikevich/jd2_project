package it.academy.cv_storage.data.utilities;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import it.academy.cv_storage.exception.ClassHasNoCorrectAnnotation;
import it.academy.cv_storage.exception.IncorrectArgumentException;
import it.academy.cv_storage.exception.NullClassEntityExeption;
import it.academy.cv_storage.exception.StartSqlSentenceExeption;
import lombok.NoArgsConstructor;

@Component
@Scope("prototype")
@NoArgsConstructor
public class CustomSqlCondition extends CustomSqlSelect {

	private boolean conditionStarted = false;

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

	// ------------- concatenators ------------------

	public CustomSqlCondition or() throws StartSqlSentenceExeption {
		return concatenate("OR");
	}
	
	public CustomSqlCondition and() throws StartSqlSentenceExeption {
		return concatenate("AND");
	}
	


	@Override
	public String getQuery() throws StartSqlSentenceExeption {
		if (conditionStarted) {
			return startQuery.toString();
		} else
			throw new StartSqlSentenceExeption("You should finish you query with some condition");
	}
	
	//----------- helper methods ------------------

	private CustomSqlCondition conditionCreate(String paramName, String value, String operator)
			throws ClassHasNoCorrectAnnotation, NullClassEntityExeption, NoSuchFieldException, StartSqlSentenceExeption,
			IncorrectArgumentException {
		if (!conditionStarted) {
			ClassInfoRetriever classInfo = new ClassInfoRetriever(clsFrom);
			String correctParamName = classInfo.getSelectParameter(paramName);

			startQuery.append(correctParamName)
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

	private CustomSqlCondition concatenate(String concatinator) throws StartSqlSentenceExeption {
		if (conditionStarted) {
			startQuery.append(" ").append(concatinator.toUpperCase()).append(" ");
			conditionStarted = false;
			return this;
		} else
			throw new StartSqlSentenceExeption(concatinator
					+ "() can be applied ONLY after conditional operation e.g. (equal, notEqual, gt etc.)");
	}
}
