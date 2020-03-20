package pl.jacekduszenko.abstr.service.impl;

import org.apache.lucene.queries.BoostingQuery;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.ConstantScoreQuery;
import org.apache.lucene.search.Query;
import org.elasticsearch.index.query.IndexQueryParserService;
import org.elasticsearch.index.query.ParsedQuery;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.jacekduszenko.abstr.config.ElasticParserServiceConfig;
import pl.jacekduszenko.abstr.data.ElasticsearchQueryProvider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticParserServiceConfig.class)
public class IndexQueryParserServiceTest {

    private final static String validLuceneBoostingDsl = "text:apple/text:pie tart fruit crumble tree^0.0";
    private final static String validLuceneMatchAllDsl = "ConstantScore(*:*)";
    private final static String validLuceneMatchDsl = "+rating:4.5 +stringField:verygood +intField:13 +boolField:true";
    private final static String validLuceneRangeDsl = "+ConstantScore(*:*) +balance:[20000 TO 30000]";
    private final static String validLuceneAggregatedDsl = "+size:0";

    @Autowired
    private IndexQueryParserService indexQueryParserService;

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

    private void testQueryFromFile(String filename, String assertedLuceneQuery, Class assertedClass) {
        //given
        String contents = ElasticsearchQueryProvider.loadElasticQueryFromFile(filename);

        //when
        ParsedQuery parsedQuery = indexQueryParserService.parse(contents);
        Query query = parsedQuery.query();

        //then
        assertThat(query.toString(), Matchers.is(assertedLuceneQuery));
        assertTrue(assertedClass.isInstance(query));
    }
}
