package it.academy.cv_storage.data.utilities;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import it.academy.cv_storage.exception.ClassHasNoCorrectAnnotation;
import it.academy.cv_storage.exception.IncorrectArgumentException;
import it.academy.cv_storage.exception.StartSqlSentenceExeption;

@Component
@Scope("prototype")
public class CustomSqlSelect extends CustomSql {
	
	
	//SELECT * from tableWithClassName
	public <T> CustomSqlSelect selectAllFrom(Class<T> clsFrom) throws StartSqlSentenceExeption, ClassHasNoCorrectAnnotation {
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
					throws StartSqlSentenceExeption, ClassHasNoCorrectAnnotation, NoSuchFieldException, SecurityException, IncorrectArgumentException {
		ClassInfoRetriever classInfo = new ClassInfoRetriever(clsFrom);
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
	public String getQuery() {
		return  startQuery.toString();
		
	}

}
