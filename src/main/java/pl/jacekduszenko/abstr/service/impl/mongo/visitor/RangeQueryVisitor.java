package pl.jacekduszenko.abstr.service.impl.mongo.visitor;

import org.apache.lucene.search.TermRangeQuery;
import pl.jacekduszenko.abstr.model.MongoQueryBuilder;
import pl.jacekduszenko.abstr.service.QueryVisitor;

public class RangeQueryVisitor implements QueryVisitor<TermRangeQuery, MongoQueryBuilder> {
    @Override
    public void visit(TermRangeQuery query, MongoQueryBuilder builder) {

    }
}
