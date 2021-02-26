package it.academy.cv_storage.data.utilities.agregation;

import it.academy.cv_storage.exception.IncorrectArgumentException;

public abstract class Aggregator {

private String paramName;
private String aggrFunc;

   public Aggregator(String paramName) throws IncorrectArgumentException {
      if(paramName == null )
         throw new IncorrectArgumentException("Argument of agregate function can't be null");
      if(paramName.trim().length()==0)
         throw new IncorrectArgumentException("Argument of agregate function can't be empty");

      this.paramName = paramName;
   }

   public String getParamName() {
      return paramName;
   }

   public void setParamName(String paramName) {
      this.paramName = paramName;
   }

   public String getAggrFunc() {
      return aggrFunc;
   }

   public void setAggrFunc(String aggrFunc) {
      this.aggrFunc = aggrFunc;
   }




}
