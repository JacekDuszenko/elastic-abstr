package pl.jacekduszenko.abstr.service.impl.mongo;

import org.apache.lucene.search.Query;
import pl.jacekduszenko.abstr.model.MongoQueryBuilder;
import pl.jacekduszenko.abstr.service.QueryVisitor;

public class MongoQueryVisitor implements QueryVisitor<MongoQueryBuilder> {

    @Override
    public void visit(Query q, MongoQueryBuilder bulder) {

    }
}
