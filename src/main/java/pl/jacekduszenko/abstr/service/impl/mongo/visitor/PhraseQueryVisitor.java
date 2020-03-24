package pl.jacekduszenko.abstr.service.impl.mongo.visitor;

import org.apache.lucene.search.PhraseQuery;
import pl.jacekduszenko.abstr.service.impl.mongo.builder.MongoQueryBuilder;
import pl.jacekduszenko.abstr.service.QueryVisitor;

public class PhraseQueryVisitor implements QueryVisitor<PhraseQuery, MongoQueryBuilder> {
    @Override
    public void visit(PhraseQuery query, MongoQueryBuilder builder) {

    }
}
