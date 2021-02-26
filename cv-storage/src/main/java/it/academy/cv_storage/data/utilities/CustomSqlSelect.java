package it.academy.cv_storage.data.utilities;


import java.util.List;
import java.util.stream.Collectors;

import it.academy.cv_storage.data.utilities.agregation.Aggregator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import it.academy.cv_storage.exception.ClassHasNoCorrectAnnotation;
import it.academy.cv_storage.exception.IncorrectArgumentException;
import it.academy.cv_storage.exception.NullClassEntityExeption;
import it.academy.cv_storage.exception.StartSqlSentenceExeption;

@Component
@Scope("prototype")
public class CustomSqlSelect extends CustomSql {

	private boolean isWherePresent = false;
	private boolean isHavingPresent = false;
	private boolean isOrderByPresent = false;
	private boolean isGroupByPresent = false;
	private boolean isAggregateFuncPresent = false;

	private StringBuilder orderByStr = new StringBuilder();
	
	//SELECT * from tableWithClassName
	public <T> CustomSqlSelect selectAllFrom(Class<T> clsFrom) throws StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
		ClassInfoRetriever classInfo = new ClassInfoRetriever(clsFrom);
		this.clsFrom = clsFrom;
		if(startQuery == null) {
		startQuery = new StringBuilder();
		startQuery.append("SELECT * from").append(" ")
				.append(classInfo.getEntityTableName())
				.append(" ");
		return this;
		}else 
			throw new StartSqlSentenceExeption(
					"The beginning of SQL query is not correct. It should start from SELECT ...");
		
	}
	
	//SELECT searchParam1, searchParam2 ... from tableWithClassName
	public <T> CustomSqlSelect selectFrom(Class<T> clsFrom, String...searchParam) 
					throws StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, NoSuchFieldException, SecurityException, IncorrectArgumentException, NullClassEntityExeption {
		if(searchParam == null) {
			throw new IncorrectArgumentException("It was inserted null instead of array of parameters");
		}
		ClassInfoRetriever classInfo = new ClassInfoRetriever(clsFrom);
		this.clsFrom = clsFrom;
		
		if(startQuery == null) {
		startQuery = new StringBuilder();
		startQuery.append("SELECT ");
			if(searchParam.length == 0) {
				startQuery.append("* ");
			}else {
				List<String> checkedSearchParam = classInfo.getSelectParameters(searchParam);
				startQuery.append(checkedSearchParam.stream()
										.map(x->x.toUpperCase())
										.collect(Collectors.joining(", ",""," ")));
			}
		
		startQuery.append("from").append(" ")
					.append(classInfo.getEntityTableName())
					.append(" ");
		return this;
		}else 
			throw new StartSqlSentenceExeption(
					"The beginning of SQL query is not correct. It should start from SELECT ...");
		
	}

	//SELECT AggFunc(searchParam1) searchParam1, searchParam2 ... from tableWithClassName
	public <T> CustomSqlSelect selectWithAggregationFrom(Class<T> clsFrom, List<Aggregator> aggregators, String...searchParam) throws StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption, NoSuchFieldException {
		if(searchParam == null) {
			throw new IncorrectArgumentException("It was inserted null instead of array of parameters");
		}
		if(aggregators == null) {
			throw new IncorrectArgumentException("It was inserted null instead of List of aggregators");
		}
		if(aggregators.size() == 0) {
			throw new IncorrectArgumentException("It was inserted empty List of aggregators");
		}
		ClassInfoRetriever classInfo = new ClassInfoRetriever(clsFrom);
		this.clsFrom = clsFrom;

		if(startQuery == null) {
			startQuery = new StringBuilder();
			startQuery.append("SELECT ");
			if(searchParam.length == 0) {
				//if searchParam haven't been inputted it would be only agg parameters
				startQuery.append(" ");
			}else {
				isAggregateFuncPresent = true;
				//retrieve List of prepared agg func e.g.  MAX(FIRST_NAME)
				List<String> checkedAggSearchParam = classInfo.getSelectAggParameters(aggregators);
				List<String> checkedSearchParam = classInfo.getSelectParameters(searchParam);
				startQuery.append(checkedAggSearchParam.stream()
						.map(x->x.toUpperCase())
						.collect(Collectors.joining(", ","","")));
				startQuery.append(",");
				startQuery.append(checkedSearchParam.stream()
						.map(x->x.toUpperCase())
						.collect(Collectors.joining(", "," ","")));
			}

			startQuery.append(" from").append(" ")
					.append(classInfo.getEntityTableName())
					.append(" ");
			return this;
		}else
			throw new StartSqlSentenceExeption(
					"The beginning of SQL query is not correct. It should start from SELECT ...");

	}

	@Override
	public String getQuery() throws StartSqlSentenceExeption {
		if(startQuery!= null && startQuery.toString().length() !=0 ) {
			return  startQuery.toString();
		}else 
			throw new StartSqlSentenceExeption(
					"The beginning of SQL query is incorrect. You should use selectAllFrom() or selectFrom() first. Without WHERE");
		
	}

	// ------------------ WHERE --------------------------------------
	
	public CustomSqlCondition where() throws StartSqlSentenceExeption {
		if(startQuery == null) {
			throw new StartSqlSentenceExeption(
				"The beginning of SQL query is incorrect. You should use selectAllFrom() or selectFrom() or selectWithAggregationFrom() first");
		}

		//everything that below WHERE can't be before
		if(isHavingPresent) {
			throw new StartSqlSentenceExeption(
					"WHERE can't be applied after HAVING. You should use first WHERE and then HAVING");
		}


		if(isGroupByPresent) {
			throw new StartSqlSentenceExeption(
					"WHERE can't be applied after GROUP BY. You should use first WHERE and then GROUP BY");
		}
		if(isOrderByPresent) {
			throw new StartSqlSentenceExeption(
				"WHERE can't be applied after ORDER BY. You should use first WHERE and then ORDER BY");
		}



			startQuery.append(" WHERE ");
			isWherePresent = true;
			CustomSqlCondition customSqlCondition = new CustomSqlCondition(startQuery);
			customSqlCondition.clsFrom = this.clsFrom;
		return customSqlCondition;
	}
	
	// ------------------ ORDER BY --------------------------------------
	public CustomSqlSelect orderBy(String field, OrderBySortingType sortingType) throws StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, NullClassEntityExeption, NoSuchFieldException, SecurityException, IncorrectArgumentException {

		if(startQuery!= null && startQuery.toString().length() !=0 ) {
			ClassInfoRetriever classInfo = new ClassInfoRetriever(clsFrom);
			String correctParamName = classInfo.getSelectParameter(field);
			
			if(sortingType == null) {
				throw new IncorrectArgumentException(
						"The sorting type is null. It should be OrderBySortingType.ASC or ...DESC");
			}

			if(!isOrderByPresent) {
				startQuery.append(" ORDER BY ")
						  .append(correctParamName)
						  .append(" ")
						  .append(sortingType.toString())
						  .append(" ");
				//flag to create ORDER BY from two or more argument of sorting
				isOrderByPresent =true;
			} else {
				startQuery.append(", ")
						  .append(correctParamName)
						  .append(" ")
						  .append(sortingType.toString())
						  .append(" ");
			}
			
			return this;
		}else 
			throw new StartSqlSentenceExeption(
					"The beginning of SQL query is incorrect. You should use selectAllFrom() or selectFrom() or selectWithAggregationFrom() first");

		}

	// ------------------ GROUP BY --------------------------------------

	public CustomSqlSelect groupBy(String field) throws StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, NullClassEntityExeption, NoSuchFieldException, SecurityException, IncorrectArgumentException {

		//everything that below GROUP BY can't be before
		if(isHavingPresent) {
			throw new StartSqlSentenceExeption(
					"GROUP BY can't be applied after HAVING. You should use first GROUP BY and then HAVING");
		}

		if(isOrderByPresent) {
			throw new StartSqlSentenceExeption(
					"GROUP BY can't be applied after ORDER BY. You should use first GROUP BY and then ORDER BY");
		}

		if(startQuery!= null && startQuery.toString().length() !=0 ) {
			ClassInfoRetriever classInfo = new ClassInfoRetriever(clsFrom);
			String correctParamName = classInfo.getSelectParameter(field);


			if(!isGroupByPresent) {
				startQuery.append(" GROUP BY ")
						.append(correctParamName)
						.append(" ");
				//flag to create ORDER BY from two or more argument of sorting
				isGroupByPresent =true;
			} else {
				startQuery.append(", ")
						.append(correctParamName)
						.append(" ");
			}

			return this;
		}else
			throw new StartSqlSentenceExeption(
					"The beginning of SQL query is incorrect. You should use selectAllFrom() or selectFrom() or selectWithAggregationFrom() first");

	}


// ------------------ HAVING --------------------------------------
public CustomSqlAggCondition having() throws StartSqlSentenceExeption {
	if(startQuery == null) {
		throw new StartSqlSentenceExeption(
				"The beginning of SQL query is incorrect. You should use selectAllFrom() or selectFrom() or selectWithAggregationFrom() first");
	}
	// if you try to use where
	if(isOrderByPresent) {
		throw new StartSqlSentenceExeption(
				"HAVING can't be applied after ORDER BY. You should use first HAVING and then ORDER BY");
	}



	startQuery.append(" HAVING ");
	isHavingPresent = true;
	CustomSqlAggCondition customSqlAggCondition = new CustomSqlAggCondition(startQuery);
	customSqlAggCondition.clsFrom = this.clsFrom;
	return customSqlAggCondition;
}
}
