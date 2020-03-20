package pl.jacekduszenko.abstr.service.impl.mongo;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import pl.jacekduszenko.abstr.model.MongoQueryBuilder;
import pl.jacekduszenko.abstr.service.LuceneTranslator;
import pl.jacekduszenko.abstr.service.QueryVisitor;

@Service
public class LuceneToMongoTranslator implements LuceneTranslator<Query> {

    @Override
    public Query translateFromLuceneQuery(org.apache.lucene.search.Query luceneQuery) {
        MongoQueryBuilder builder = MongoQueryBuilder.create();
        QueryVisitor<MongoQueryBuilder> visitor = new MongoQueryVisitor();
        visitor.visit(luceneQuery, builder);
        return builder.build();
    }
}
