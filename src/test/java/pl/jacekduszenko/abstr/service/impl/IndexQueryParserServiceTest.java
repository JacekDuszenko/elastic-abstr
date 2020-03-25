package pl.jacekduszenko.abstr.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.lucene.queries.BoostingQuery;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.ConstantScoreQuery;
import org.apache.lucene.search.Query;
import org.elasticsearch.index.query.IndexQueryParserService;
import org.elasticsearch.index.query.ParsedQuery;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.jacekduszenko.abstr.config.ElasticParserServiceConfig;
import pl.jacekduszenko.abstr.config.ObjectMapperConfig;
import pl.jacekduszenko.abstr.data.ElasticsearchQueryProvider;
import pl.jacekduszenko.abstr.service.impl.mongo.MongoQueryPartExtractor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {ElasticParserServiceConfig.class, ObjectMapperConfig.class})
public class IndexQueryParserServiceTest {

    private final static String validLuceneBoostingDsl = "text:apple/text:pie tart fruit crumble tree^0.0";
    private final static String validLuceneMatchAllDsl = "ConstantScore(*:*)";
    private final static String validLuceneMatchDsl = "+rating:4.5 +stringField:verygood +intField:13 +boolField:true";
    private final static String validLuceneRangeDsl = "+ConstantScore(*:*) +balance:[20000 TO 30000]";
    private final static String validLuceneAggregatedDsl = "+size:0";

    @Autowired
    private IndexQueryParserService indexQueryParserService;
    @Autowired
    private ObjectMapper objectMapper;

    private MongoQueryPartExtractor mongoQueryPartExtractor;

    @BeforeEach
    public void setUp() {
        mongoQueryPartExtractor = new MongoQueryPartExtractor(objectMapper);
    }

    @Test
    public void shouldParseBoostingQueryCorrectly() {
        testQueryFromFile("boosting_query.json", validLuceneBoostingDsl, BoostingQuery.class);
    }

    @Test
    public void shouldParseMatchAllQueryCorrectly() {
        testQueryFromFile("match_all_query.json", validLuceneMatchAllDsl, ConstantScoreQuery.class);
    }

    @Test
    public void shouldParseMatchQueryCorrectly() {
        testQueryFromFile("match_query.json", validLuceneMatchDsl, BooleanQuery.class);
    }

    @Test
    public void shouldParseRangeQueryCorrectly() {
        testQueryFromFile("match_range_query.json", validLuceneRangeDsl, BooleanQuery.class);
    }

    @Test
    public void shouldParseAggregationQueryCorrectly() {
        testQueryFromFile("aggregated_query.json", validLuceneAggregatedDsl, BooleanQuery.class);
    }

    @SneakyThrows
    private void testQueryFromFile(String filename, String assertedLuceneQuery, Class assertedClass) {
        //given
        String contents = ElasticsearchQueryProvider.loadElasticQueryFromFile(filename);
        String matchQueryPart = mongoQueryPartExtractor.extractMatchAndAggregatePartFromQuery(contents)._1;

        //when
        ParsedQuery parsedQuery = indexQueryParserService.parse(matchQueryPart);
        Query query = parsedQuery.query();

        //then
        assertThat(query.toString(), Matchers.is(assertedLuceneQuery));
        assertTrue(assertedClass.isInstance(query));
    }
}
