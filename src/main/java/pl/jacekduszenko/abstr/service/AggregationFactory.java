package pl.jacekduszenko.abstr.service;

public interface AggregationFactory<R, String> {

    R fromAggregationString(String queryString);
}
