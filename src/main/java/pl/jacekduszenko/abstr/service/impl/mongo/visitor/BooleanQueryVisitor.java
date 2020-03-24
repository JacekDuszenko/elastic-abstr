package pl.jacekduszenko.abstr.service.impl.mongo.visitor;

import lombok.SneakyThrows;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import pl.jacekduszenko.abstr.service.impl.mongo.builder.MongoQueryBuilder;
import pl.jacekduszenko.abstr.service.QueryVisitor;
import pl.jacekduszenko.abstr.service.impl.mongo.MongoVisitorFactory;

public class BooleanQueryVisitor implements QueryVisitor<BooleanQuery, MongoQueryBuilder> {
    @Override
    public void visit(BooleanQuery query, MongoQueryBuilder builder) {
        handleBooleanQuery(query, builder);
    }

    private void handleBooleanQuery(BooleanQuery boolQuery, MongoQueryBuilder builder) {
        boolQuery.clauses().forEach(clause -> visitChild(builder, clause));
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    private void visitChild(MongoQueryBuilder builder, BooleanClause clause) {
        QueryVisitor visitor = MongoVisitorFactory.createVisitorForQuery(clause.getQuery());
        //TODO write a boolean query context aware wrapper visitor and decorate visitor with it, after that, just visit from this visitor.
        //TODO this is needed for logical expressions in mongo (or vs and vs xor)
        visitor.visit(clause.getQuery(), builder);
    }
}
