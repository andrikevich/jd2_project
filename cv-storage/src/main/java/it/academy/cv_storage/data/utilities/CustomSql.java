package it.academy.cv_storage.data.utilities;

public abstract  class CustomSql {
	
	protected StringBuilder startQuery = null;
	protected StringBuilder whereQuery = null;
	
	// class where we using reflection will be retrieved data
	protected Class<?> clsFrom = null;
	
	public abstract String getQuery();

}
