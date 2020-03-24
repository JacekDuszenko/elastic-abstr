package pl.jacekduszenko.abstr.service.impl.mongo.visitor;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.TermQuery;
import pl.jacekduszenko.abstr.service.impl.mongo.builder.MongoQueryBuilder;
import pl.jacekduszenko.abstr.service.QueryVisitor;

@Slf4j
public class TermQueryVisitor implements QueryVisitor<TermQuery, MongoQueryBuilder> {

    @Override
    public void visit(TermQuery query, MongoQueryBuilder builder) {
        builder.withIsClause(query.getTerm().field(), query.getTerm().text());
    }
}
