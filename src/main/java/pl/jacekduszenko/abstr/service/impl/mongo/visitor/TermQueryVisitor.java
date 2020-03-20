package pl.jacekduszenko.abstr.service.impl.mongo.visitor;

import org.apache.lucene.search.TermQuery;
import pl.jacekduszenko.abstr.model.MongoQueryBuilder;
import pl.jacekduszenko.abstr.service.QueryVisitor;

public class TermQueryVisitor implements QueryVisitor<TermQuery, MongoQueryBuilder> {
    @Override
    public void visit(TermQuery query, MongoQueryBuilder builder) {

    }
}
