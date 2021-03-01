package it.academy.cv_storage.data.utilities.criteria;

import it.academy.cv_storage.exception.StartSqlSentenceExeption;

public abstract  class CustomSql {
	
	protected StringBuilder startQuery = null;
	
	// class where we using reflection will be retrieved data
	protected Class<?> clsFrom = null;
	
	public abstract String getQuery() throws StartSqlSentenceExeption;

}
