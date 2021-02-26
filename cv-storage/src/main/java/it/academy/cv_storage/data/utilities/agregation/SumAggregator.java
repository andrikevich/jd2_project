package it.academy.cv_storage.data.utilities.agregation;

import it.academy.cv_storage.exception.IncorrectArgumentException;

public class SumAggregator extends Aggregator{
    public SumAggregator(String paramName) throws IncorrectArgumentException {
        super(paramName);
        setAggrFunc("SUM");
    }
}
