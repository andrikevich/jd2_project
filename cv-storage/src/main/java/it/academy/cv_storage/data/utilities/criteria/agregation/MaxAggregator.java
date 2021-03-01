package it.academy.cv_storage.data.utilities.criteria.agregation;

import it.academy.cv_storage.exception.IncorrectArgumentException;

public class MaxAggregator extends Aggregator {

    public MaxAggregator(String paramName) throws IncorrectArgumentException {
        super(paramName);
        setAggrFunc("MAX");
    }


}
