package it.academy.cv_storage.data.utilities;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import it.academy.cv_storage.exception.ClassHasNoCorrectAnnotation;
import it.academy.cv_storage.exception.IncorrectArgumentException;
import it.academy.cv_storage.exception.NullClassEntityExeption;
import it.academy.cv_storage.exception.StartSqlSentenceExeption;

@Component
@Scope("prototype")
public class CustomSqlSelect extends CustomSql {
	

	private boolean isOrderByPresent = false;
	
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
										.collect(Collectors.joining(", "," "," ")));
			}
		
		startQuery.append("from").append(" ")
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
				"The beginning of SQL query is incorrect. You should use selectAllFrom() or selectFrom() first");
		}
		
		if(isOrderByPresent) {
			throw new StartSqlSentenceExeption(
				"WHERE can't be applyed after ORDER BY. You should use first WHERE and then ORDER BY");
		}

			startQuery.append(" WHERE ");
			CustomSqlCondition customSqlCondition = new CustomSqlCondition(startQuery);
			customSqlCondition.clsFrom = this.clsFrom;
		return customSqlCondition;
	}
	
	// ------------------ ORDERBY --------------------------------------
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
					"The beginning of SQL query is incorrect. You should use selectAllFrom() or selectFrom() first.");

		}
		

}
