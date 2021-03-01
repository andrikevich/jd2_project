package it.academy.cv_storage.data.utilities.criteria.agregation;

import it.academy.cv_storage.exception.IncorrectArgumentException;

public class AvgAggregator extends Aggregator{
    public AvgAggregator(String paramName) throws IncorrectArgumentException {
        super(paramName);
        setAggrFunc("AVG");
    }
}
