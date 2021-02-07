package it.academy.cv_storage.data.utilities;

public abstract  class CustomSql {
	
	protected StringBuilder startQuery = null;
	protected StringBuilder whereQuery = null;
	
	public abstract String getQuery();

}
