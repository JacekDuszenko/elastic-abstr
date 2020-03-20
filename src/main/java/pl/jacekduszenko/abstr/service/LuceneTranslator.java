package pl.jacekduszenko.abstr.service;

import org.apache.lucene.search.Query;

public interface LuceneTranslator<T> {

    T translateFromLuceneQuery(Query q);
}
