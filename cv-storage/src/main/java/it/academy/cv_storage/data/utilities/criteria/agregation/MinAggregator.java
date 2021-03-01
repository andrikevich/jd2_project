package it.academy.cv_storage.data.utilities.criteria.agregation;

import it.academy.cv_storage.exception.IncorrectArgumentException;

public class MinAggregator extends Aggregator{
    public MinAggregator(String paramName) throws IncorrectArgumentException {
        super(paramName);
        setAggrFunc("MIN");
    }


}

