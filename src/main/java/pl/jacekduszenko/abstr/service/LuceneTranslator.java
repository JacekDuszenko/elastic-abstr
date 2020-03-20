package pl.jacekduszenko.abstr.service;

import org.apache.lucene.search.Query;
import pl.jacekduszenko.abstr.model.exception.VisitorCreationException;

public interface LuceneTranslator<T> {

    T translateFromLuceneQuery(Query q) throws VisitorCreationException;
}
