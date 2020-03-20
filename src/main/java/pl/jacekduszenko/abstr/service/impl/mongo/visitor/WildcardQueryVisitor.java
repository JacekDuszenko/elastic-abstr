package pl.jacekduszenko.abstr.service.impl.mongo.visitor;

import org.apache.lucene.search.WildcardQuery;
import pl.jacekduszenko.abstr.model.MongoQueryBuilder;
import pl.jacekduszenko.abstr.service.QueryVisitor;

public class WildcardQueryVisitor implements QueryVisitor<WildcardQuery, MongoQueryBuilder> {

    @Override
    public void visit(WildcardQuery query, MongoQueryBuilder builder) {

    }
}
