package pl.jacekduszenko.abstr.controller;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.servlet.MockMvc;
import pl.jacekduszenko.abstr.AbstrApplication;
import pl.jacekduszenko.abstr.api.ApiCaller;
import pl.jacekduszenko.abstr.data.ElasticsearchQueryProvider;
import pl.jacekduszenko.abstr.integration.mongo.MongoDataFeed;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static pl.jacekduszenko.abstr.integration.mongo.MongoDataFeed.COLLECTION_NAME;

@SpringBootTest(classes = {AbstrApplication.class, MongoDataFeed.class})
@AutoConfigureMockMvc
public class MongoCompoundAggregationIntegrationTest {

    private final static int CORRECT_DOCS_LIST_SIZE = 3;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MockMvc mockMvc;

    private ApiCaller apiCaller;

    @BeforeEach
    public void setUp() {
        apiCaller = new ApiCaller(mockMvc);
    }

    @AfterEach
    public void tearDown() {
        mongoTemplate.getDb().drop();
    }

    @Test
    public void shouldReturnCorrectDocCountWithZeroNestedAggregationsQuery() throws Exception {
        //when
        String queryString = ElasticsearchQueryProvider.loadElasticQueryFromFile("aggregation_plain_term_query.json");
        java.util.List<Map<String, Object>> docs = apiCaller.callSearchApi(queryString, COLLECTION_NAME, false);

        //then
        System.out.println(docs);
        assertThat(docs.size(), CoreMatchers.is(CORRECT_DOCS_LIST_SIZE));
    }

    @Test
    public void shouldReturnCorrectExpiryDatesOnSingleNestedHistogramAggregation() throws Exception {
        //when
        String queryString = ElasticsearchQueryProvider.loadElasticQueryFromFile("aggregation_term_date_histogram_query.json");
        java.util.List<Map<String, Object>> docs = apiCaller.callSearchApi(queryString, COLLECTION_NAME, false);

        //then
        System.out.println(docs);
        assertThat(docs.size(), CoreMatchers.is(CORRECT_DOCS_LIST_SIZE));
    }
}