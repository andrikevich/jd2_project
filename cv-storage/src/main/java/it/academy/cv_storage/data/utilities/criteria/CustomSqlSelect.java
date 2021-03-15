package it.academy.cv_storage.data.utilities.criteria;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.academy.cv_storage.data.utilities.helper.ClassInfoRetriever;
import it.academy.cv_storage.data.utilities.helper.OrderBySortingType;
import it.academy.cv_storage.data.utilities.criteria.agregation.Aggregator;
import it.academy.cv_storage.exception.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CustomSqlSelect extends CustomSql {
	protected boolean isSelectPresent = false;
	protected boolean isWherePresent = false;
	protected boolean isHavingPresent = false;
	private boolean isOrderByPresent = false;
	private boolean isGroupByPresent = false;
	private boolean isAggregateFuncPresent = false;


	protected  List<String> paramOfQuery = new ArrayList<>();

	public List<String> getParamOfQuery() {
		return paramOfQuery;
	}


	//SELECT * from tableWithClassName
	public <T> CustomSqlSelect selectAllFrom(Class<T> clsFrom) throws StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, NullClassEntityExeption {
		if(isSelectPresent){
			throw new StartSqlSentenceExeption(
					"Second usage SELECT is not permit. If you tried subquery create a new instance of customSQL");
		}

		ClassInfoRetriever classInfo = new ClassInfoRetriever(clsFrom);
		this.clsFrom = clsFrom;
		if(startQuery == null) {
		isSelectPresent = true;
		startQuery = new StringBuilder();
		startQuery.append("SELECT * from").append(" ")
				.append(classInfo.getEntityTableName())
				.append(" t0 LEFT JOIN skype t1 ON t0.ID=t1.CANDIDATE_ID")
				.append(" LEFT JOIN email t2 ON t0.ID=t2.CANDIDATE_ID")
				.append(" LEFT JOIN phone t3 ON t0.ID=t3.CANDIDATE_ID")
				.append(" LEFT JOIN site t4 ON t0.ID=t4.CANDIDATE_ID")
				.append(" LEFT JOIN candidate_knowledge t5 ON t0.ID=t5.CANDIDATE_ID")
				.append(" JOIN knowledge t6 ON t5.KNOWLEDGE_ID = t6.KNOWLEDGE_ID");
		return this;
		}else 
			throw new StartSqlSentenceExeption(
					"The beginning of SQL query is not correct. It should start from SELECT ...");
		
	}
	
	//SELECT searchParam1, searchParam2 ... from tableWithClassName
	public <T> CustomSqlSelect selectFrom(Class<T> clsFrom, String...searchParam) 
					throws StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, NoSuchFieldException, SecurityException, IncorrectArgumentException, NullClassEntityExeption {
		if(isSelectPresent){
			throw new StartSqlSentenceExeption(
					"Second usage SELECT is not permit. If you tried subquery, create a new instance of customSQL");
		}

		if(searchParam == null) {
			throw new IncorrectArgumentException("It was inserted null instead of array of parameters");
		}
		ClassInfoRetriever classInfo = new ClassInfoRetriever(clsFrom);
		this.clsFrom = clsFrom;
		
		if(startQuery == null) {
			isSelectPresent = true;
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
					.append(" t0 LEFT JOIN skype t1 ON t0.ID=t1.CANDIDATE_ID")
					.append(" LEFT JOIN email t2 ON t0.ID=t2.CANDIDATE_ID")
					.append(" LEFT JOIN phone t3 ON t0.ID=t3.CANDIDATE_ID")
					.append(" LEFT JOIN site t4 ON t0.ID=t4.CANDIDATE_ID")
					.append(" LEFT JOIN candidate_knowledge t5 ON t0.ID=t5.CANDIDATE_ID")
					.append(" JOIN knowledge t6 ON t5.KNOWLEDGE_ID = t6.KNOWLEDGE_ID");
		return this;
		}else 
			throw new StartSqlSentenceExeption(
					"The beginning of SQL query is not correct. It should start from SELECT ...");
		
	}

	//SELECT AggFunc(searchParam1) searchParam1, searchParam2 ... from tableWithClassName
	// applied several aggregation functions(List)
	public <T> CustomSqlSelect selectWithAggregationFrom(
			Class<T> clsFrom, List<Aggregator> aggregators, String...searchParam) throws StartSqlSentenceExeption, IncorrectArgumentException, ClassHasNoCorrectAnnotation, NullClassEntityExeption, NoSuchFieldException {
		if(isSelectPresent){
			throw new StartSqlSentenceExeption(
					"Second usage SELECT is not permit. If you tried subquery create a new instance of customSQL");
		}

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
			isSelectPresent = true;
			startQuery = new StringBuilder();
			startQuery.append("SELECT ");

			isAggregateFuncPresent = true;
			//retrieve List of prepared agg func e.g.  MAX(FIRST_NAME)
			List<String> checkedAggSearchParam = classInfo.getSelectAggParameters(aggregators);
			startQuery.append(checkedAggSearchParam.stream()
					.map(x->x.toUpperCase())
					.collect(Collectors.joining(", ","","")));

			// add regular (non aggregate) fields to select sentence
			if(searchParam.length == 0) {
				//if searchParam haven't been inputted it would be only agg parameters
				startQuery.append(" ");
			}else {
				List<String> checkedSearchParam = classInfo.getSelectParameters(searchParam);
				startQuery.append(",");
				startQuery.append(checkedSearchParam.stream()
						.map(x->x.toUpperCase())
						.collect(Collectors.joining(", "," ","")));
			}

			startQuery.append(" from").append(" ")
					.append(classInfo.getEntityTableName())
					.append(classInfo.getEntityTableName())
					.append(" t0 LEFT JOIN skype t1 ON t0.ID=t1.CANDIDATE_ID")
					.append(" LEFT JOIN email t2 ON t0.ID=t2.CANDIDATE_ID")
					.append(" LEFT JOIN phone t3 ON t0.ID=t3.CANDIDATE_ID")
					.append(" LEFT JOIN site t4 ON t0.ID=t4.CANDIDATE_ID")
					.append(" LEFT JOIN candidate_knowledge t5 ON t0.ID=t5.CANDIDATE_ID")
					.append(" JOIN knowledge t6 ON t5.KNOWLEDGE_ID = t6.KNOWLEDGE_ID");
			return this;
		}else
			throw new StartSqlSentenceExeption(
					"The beginning of SQL query is not correct. It should start from SELECT ...");

	}
	//SELECT AggFunc(searchParam1) searchParam1, searchParam2 ... from tableWithClassName
	// applied single  aggregation function and param
	public <T> CustomSqlSelect selectWithAggregationFrom(Class<T> clsFrom, Aggregator aggregator, String...searchParam)
			throws NoSuchFieldException, IncorrectArgumentException, NullClassEntityExeption, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation {
		if(aggregator == null)
			throw new IncorrectArgumentException("It was inserted null instead of Aggregation");
		return selectWithAggregationFrom(clsFrom,List.of(aggregator),searchParam);
	}

	// applied single  aggregation function
	public <T> CustomSqlSelect selectWithAggregationFrom(Class<T> clsFrom, Aggregator aggregator)
			throws NoSuchFieldException, IncorrectArgumentException, NullClassEntityExeption, StartSqlSentenceExeption, ClassHasNoCorrectAnnotation {
		if(aggregator == null)
			throw new IncorrectArgumentException("It was inserted null instead of Aggregation");
		return selectWithAggregationFrom(clsFrom,List.of(aggregator),new String[0]);
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
	
	public CustomSqlCondition where() throws StartSqlSentenceExeption{
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

		// to exclude double using WHERE
		if(isWherePresent){
			throw new StartSqlSentenceExeption(
					"Double using WHERE is not permitted");
		}

			startQuery.append(" WHERE ");
			isWherePresent = true;
			CustomSqlCondition customSqlCondition = new CustomSqlCondition(startQuery);
			customSqlCondition.clsFrom = this.clsFrom;
			customSqlCondition.isWherePresent = isWherePresent;
			customSqlCondition.isSelectPresent = isSelectPresent;
			customSqlCondition.paramOfQuery=this.paramOfQuery;
		return customSqlCondition;
	}
	
	// ------------------ ORDER BY --------------------------------------
	public CustomSqlSelect orderBy(String field, OrderBySortingType sortingType) throws StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, NullClassEntityExeption, NoSuchFieldException, SecurityException, IncorrectArgumentException {

		if(startQuery!= null && startQuery.toString().length() !=0 ) {
			ClassInfoRetriever classInfo = new ClassInfoRetriever(clsFrom);
			String correctParamName = classInfo.getSelectParameter(clsFrom,field);
			
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

	public CustomSqlSelect orderBy(Aggregator aggPar, OrderBySortingType sortingType) throws StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, NullClassEntityExeption, NoSuchFieldException, SecurityException, IncorrectArgumentException {

		if(startQuery!= null && startQuery.toString().length() !=0 ) {
			ClassInfoRetriever classInfo = new ClassInfoRetriever(clsFrom);
			String correctParamName = classInfo.getSelectParameter(clsFrom,aggPar.getParamName());

			if(sortingType == null) {
				throw new IncorrectArgumentException(
						"The sorting type is null. It should be OrderBySortingType.ASC or ...DESC");
			}

			if(!isOrderByPresent) {
				startQuery.append(" ORDER BY ")
						.append(aggPar.getAggrFunc())
						.append("(")
						.append(correctParamName)
						.append(") ")
						.append(sortingType.toString())
						.append(" ");
				//flag to create ORDER BY from two or more argument of sorting
				isOrderByPresent =true;
			} else {
				startQuery.append(", ")
						.append(aggPar.getAggrFunc())
						.append("(")
						.append(correctParamName)
						.append(") ")
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
			String correctParamName = classInfo.getSelectParameter(clsFrom,field);


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
public CustomSqlAggCondition having() throws StartSqlSentenceExeption, FinishSqlSentenceExeption {
	if(startQuery == null) {
		throw new StartSqlSentenceExeption(
				"The beginning of SQL query is incorrect. You should use selectAllFrom() or selectFrom() or selectWithAggregationFrom() first");
	}
	// if you try to use where
	if(isOrderByPresent) {
		throw new StartSqlSentenceExeption(
				"HAVING can't be applied after ORDER BY. You should use first HAVING and then ORDER BY");
	}
// to exclude double using HAVING
	if(isHavingPresent){
		throw new FinishSqlSentenceExeption(
				"Double using HAVING is not permitted");
	}

	isHavingPresent = true;
	startQuery.append(" HAVING ");
	CustomSqlAggCondition customSqlAggCondition = new CustomSqlAggCondition(startQuery);
	customSqlAggCondition.conditionWhereStarted = false;
	customSqlAggCondition.isHavingPresent=isHavingPresent;
	customSqlAggCondition.isSelectPresent=isSelectPresent;
	customSqlAggCondition.clsFrom = this.clsFrom;
	customSqlAggCondition.paramOfQuery=this.paramOfQuery;
	return customSqlAggCondition;
}
}
