package it.academy.cv_storage.data.utilities;

import java.util.Arrays;
import java.util.stream.Collectors;

import it.academy.cv_storage.exception.ClassHasNoCorrectAnnotation;
import it.academy.cv_storage.exception.StartSqlSentenceExeption;

public class CustomSqlSelect extends CustomSql {
	
	
	//SELECT * from tableWithClassName
	public <T> CustomSqlSelect selectAllFrom(Class<T> clsFrom) throws StartSqlSentenceExeption, ClassHasNoCorrectAnnotation {
		if(startQuery == null) {
		startQuery = new StringBuilder();
		startQuery.append("from").append(" ")
				.append(ClassInfoRetriever.getEntityTableName(clsFrom))
				.append(" ");
		return this;
		}else 
			throw new StartSqlSentenceExeption("The beginning of SQL query is not correct. It should start from SELECT ...");
		
	}
	
	//SELECT searchParam1, searchParam2 ... from tableWithClassName
	public <T> CustomSqlSelect selectFrom(Class<T> clsFrom, String...searchParam) throws StartSqlSentenceExeption, ClassHasNoCorrectAnnotation {
		if(startQuery == null) {
		startQuery = new StringBuilder();
		startQuery.append("SELECT ");
			if(searchParam.length == 0) {
				startQuery.append("* ");
			}else {
				startQuery.append(Arrays.stream(searchParam)
										.map(x->x.toUpperCase())
										.collect(Collectors.joining(", "," "," ")));
			}
		
		startQuery.append("from").append(" ")
					.append(ClassInfoRetriever.getEntityTableName(clsFrom))
					.append(" ");
		return this;
		}else 
			throw new StartSqlSentenceExeption("The beginning of SQL query is not correct. It should start from SELECT ...");
		
	}
	
	@Override
	public String getQuery() {
		return  startQuery.toString();
		
	}

}
