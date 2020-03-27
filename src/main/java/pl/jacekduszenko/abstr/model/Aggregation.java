package pl.jacekduszenko.abstr.model;

import org.elasticsearch.search.aggregations.AggregationExecutionException;

public interface Aggregation<R> {

    R convertToLanguageSpecific() throws AggregationExecutionException;
}
