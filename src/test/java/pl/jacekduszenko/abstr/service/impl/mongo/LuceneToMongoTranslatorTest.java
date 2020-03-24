package pl.jacekduszenko.abstr.service.impl.mongo;

import org.apache.lucene.search.Query;
import org.junit.Before;
import org.junit.Test;
import pl.jacekduszenko.abstr.data.LuceneQueryProvider;
import pl.jacekduszenko.abstr.model.exception.TranslationException;
import pl.jacekduszenko.abstr.model.exception.VisitorCreationException;

import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LuceneToMongoTranslatorTest {

    private static final String multiMatchQueryString = "+rating:4.5 +stringField:verygood +intField:13 +boolField:true";

    private LuceneToMongoTranslator luceneToMongoTranslator;

    @Before
    public void setUp() {
        luceneToMongoTranslator = new LuceneToMongoTranslator();
    }

    @Test
    public void translateBasicBooleanQuery() throws VisitorCreationException, TranslationException {
        //given
        Query booleanQuery = LuceneQueryProvider.fromString(multiMatchQueryString);

        //when
        org.springframework.data.mongodb.core.query.Query q = luceneToMongoTranslator.translateFromLuceneQuery(booleanQuery);
        Map<String, Object> result = q.getQueryObject();

        //then
        assertThat(result.get("rating"), is(4.5));
        assertThat(result.get("stringField").toString(), is(Pattern.compile("^verygood$").toString()));
        assertThat(result.get("intField"), is(13));
        assertThat(result.get("boolField"), is(true));
    }
}