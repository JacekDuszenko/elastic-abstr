package pl.jacekduszenko.abstr.service.impl.mongo;

import lombok.SneakyThrows;
import org.apache.lucene.search.Query;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jacekduszenko.abstr.data.LuceneQueryProvider;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class LuceneToMongoTranslatorTest {

    private static final String multiMatchQueryString = "+rating:4.5 +stringField:verygood +intField:13 +boolField:true";
    public static final String closedIntervalsRangeQueryString = "+balance:[20000 TO 30000]";
    public static final String openIntervalsRangeQueryString = "+balance:{20000 TO 30000}";
    public static final String leftOpenRightClosedIntervalQueryString = "+balance:{20000 TO 30000]";
    public static final String dateRangeQueryString = "expirationDate:{2020-03-03T00:00:00.000Z TO 2020-03-06T00:00:00.000Z}";

    private LuceneToMongoTranslator luceneToMongoTranslator;

    @BeforeEach
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
    public void shouldTranslateClosedIntervalsQueryString() {
        //when
        Map<String, Object> result = mongoQueryAsMap(closedIntervalsRangeQueryString);

        //then
        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(1));
        Document range = (Document) result.get("balance");
        assertThat(range.get("$gte"), is(20000));
        assertThat(range.get("$lte"), is(30000));
    }

    @Test
    public void shouldTranslateOpenIntervalsQueryString() {
        //when
        Map<String, Object> result = mongoQueryAsMap(openIntervalsRangeQueryString);

        //then
        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(1));
        Document range = (Document) result.get("balance");
        assertThat(range.get("$gt"), is(20000));
        assertThat(range.get("$lt"), is(30000));
    }

    @Test
    public void shouldTranslateLeftOpenRightClosedIntervalQueryString() {
        //when
        Map<String, Object> result = mongoQueryAsMap(leftOpenRightClosedIntervalQueryString);

        //then
        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(1));
        Document range = (Document) result.get("balance");
        assertThat(range.get("$gt"), is(20000));
        assertThat(range.get("$lte"), is(30000));
    }


    @Test
    public void shouldTranslateDateRangeQueryString() {
        //given
        LocalDateTime validDateFrom = LocalDateTime.of(2020, 3, 3, 0, 0);
        LocalDateTime validDateTo = LocalDateTime.of(2020, 3, 6, 0, 0);

        //when
        Map<String, Object> result = mongoQueryAsMap(dateRangeQueryString);

        //then
        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(1));
        Document dateRange = (Document) result.get("expiration_date");
        assertThat(dateRange.get("$gt"), is(notNullValue()));
        assertThat(dateRange.get("$lt"), is(notNullValue())); //TODO change

    }

    @SneakyThrows
    private Map<String, Object> mongoQueryAsMap(String queryString) {
        Query luceneQ = LuceneQueryProvider.fromString(queryString);
        org.springframework.data.mongodb.core.query.Query mongoQ = luceneToMongoTranslator.translateFromLuceneQuery(luceneQ);
        return mongoQ.getQueryObject();
    }
}