package pl.jacekduszenko.abstr.service.impl.mongo.visitor;

import org.apache.lucene.search.BooleanQuery;
import pl.jacekduszenko.abstr.model.MongoQueryBuilder;
import pl.jacekduszenko.abstr.service.QueryVisitor;

public class BooleanQueryVisitor implements QueryVisitor<BooleanQuery, MongoQueryBuilder> {
    @Override
    public void visit(BooleanQuery query, MongoQueryBuilder builder) {

    }
}
