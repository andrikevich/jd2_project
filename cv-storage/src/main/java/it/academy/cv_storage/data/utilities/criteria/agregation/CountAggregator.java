package it.academy.cv_storage.data.utilities.criteria.agregation;

import it.academy.cv_storage.exception.IncorrectArgumentException;

public class CountAggregator extends Aggregator{
    public CountAggregator(String paramName) throws IncorrectArgumentException {
        super(paramName);
        setAggrFunc("COUNT");
    }
}
