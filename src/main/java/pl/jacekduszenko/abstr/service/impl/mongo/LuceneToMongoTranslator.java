package pl.jacekduszenko.abstr.service.impl.mongo;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import pl.jacekduszenko.abstr.model.exception.TranslationException;
import pl.jacekduszenko.abstr.model.exception.VisitorCreationException;
import pl.jacekduszenko.abstr.service.LuceneTranslator;
import pl.jacekduszenko.abstr.service.QueryVisitor;
import pl.jacekduszenko.abstr.service.impl.mongo.builder.MongoQueryBuilder;

@Service
public class LuceneToMongoTranslator implements LuceneTranslator<Query> {

    @Override
    public Query translateFromLuceneQuery(org.apache.lucene.search.Query luceneQuery) throws VisitorCreationException, TranslationException {
        return translateMatchQuery(luceneQuery);
    }

    @SuppressWarnings("unchecked")
    private Query translateMatchQuery(org.apache.lucene.search.Query luceneQuery) throws VisitorCreationException, TranslationException {
        MongoQueryBuilder builder = MongoQueryBuilder.create();
        QueryVisitor visitor = MongoVisitorFactory.createVisitorForQuery(luceneQuery);
        visitor.visit(luceneQuery, builder);
        return builder.build();
    }
}
