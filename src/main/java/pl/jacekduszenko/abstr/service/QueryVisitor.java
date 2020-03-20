package pl.jacekduszenko.abstr.service;

import org.apache.lucene.search.Query;

public interface QueryVisitor<B> {

    void visit(Query q, B bulder);
}
