package pl.jacekduszenko.abstr.service;

import io.vavr.Tuple2;
import pl.jacekduszenko.abstr.model.exception.TranslationException;

public interface QueryPartExtractor<M, A> {

    Tuple2<M, A> extractMatchAndAggregatePartFromQuery(String queryString) throws TranslationException;
}
