package pl.jacekduszenko.abstr.service.impl.mongo;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.query.Criteria;
import pl.jacekduszenko.abstr.data.LuceneQueryProvider;
import pl.jacekduszenko.abstr.model.exception.TranslationException;
import pl.jacekduszenko.abstr.model.exception.VisitorCreationException;

public class LuceneToMongoTranslatorTest {

    private static final String multiMatchQueryString = "+rating:4.5 +stringField:verygood +intField:13 +boolField:true";

    private LuceneToMongoTranslator luceneToMongoTranslator;

    @Before
    public void setUp() throws Exception {
        luceneToMongoTranslator = new LuceneToMongoTranslator();
    }

    @Test
    public void translateBasicBooleanQuery() throws ParseException, VisitorCreationException, TranslationException {
        Query booleanQuery = LuceneQueryProvider.fromString(multiMatchQueryString);
        org.springframework.data.mongodb.core.query.Query q = luceneToMongoTranslator.translateFromLuceneQuery(booleanQuery);
        System.out.println(q.getFieldsObject());
    }
}