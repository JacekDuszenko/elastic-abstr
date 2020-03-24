package pl.jacekduszenko.abstr.service.impl.mongo.visitor;

import org.apache.lucene.search.PrefixQuery;
import pl.jacekduszenko.abstr.service.impl.mongo.builder.MongoQueryBuilder;
import pl.jacekduszenko.abstr.service.QueryVisitor;

public class PrefixQueryVisitor implements QueryVisitor<PrefixQuery, MongoQueryBuilder> {
    @Override
    public void visit(PrefixQuery query, MongoQueryBuilder builder) {

    }
}
