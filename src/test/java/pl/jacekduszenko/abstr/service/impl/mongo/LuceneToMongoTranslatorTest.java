package pl.jacekduszenko.abstr.service.impl.mongo;

import lombok.SneakyThrows;
import org.apache.lucene.search.Query;
import org.junit.Before;
import org.junit.Test;
import pl.jacekduszenko.abstr.data.LuceneQueryProvider;

import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class LuceneToMongoTranslatorTest {

    private static final String multiMatchQueryString = "+rating:4.5 +stringField:verygood +intField:13 +boolField:true";
    public static final String rangeQueryString = "+balance:[20000 TO 30000]";

    private LuceneToMongoTranslator luceneToMongoTranslator;

    @Before
    public void setUp() {
        luceneToMongoTranslator = new LuceneToMongoTranslator();
    }

    @Test
    public void translateBasicBooleanQuery() {
        //when
        Map<String, Object> result = mongoQueryAsMap(multiMatchQueryString);

        //then
        assertThat(result.get("rating"), is(4.5));
        assertThat(result.get("stringField").toString(), is(Pattern.compile("^verygood$").toString()));
        assertThat(result.get("intField"), is(13));
        assertThat(result.get("boolField"), is(true));
    }

    @Test
    public void translateRangeQuery() {
        //when
        Map<String, Object> result = mongoQueryAsMap(rangeQueryString);

        //then
        assertThat(result, is(notNullValue()));

    }

    @SneakyThrows
    private Map<String, Object> mongoQueryAsMap(String queryString) {
        Query luceneQ = LuceneQueryProvider.fromString(queryString);
        org.springframework.data.mongodb.core.query.Query mongoQ = luceneToMongoTranslator.translateFromLuceneQuery(luceneQ);
        return mongoQ.getQueryObject();
    }
}