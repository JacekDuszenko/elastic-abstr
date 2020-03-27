package pl.jacekduszenko.abstr.controller;

import es.query.builder.ElasticQueryStringBuilder;
import io.vavr.Tuple;
import io.vavr.collection.List;
import org.bson.Document;
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

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(classes = AbstrApplication.class)
@AutoConfigureMockMvc
public class MongoQueryIntegrationTest {

    private final ElasticQueryStringBuilder builder = new ElasticQueryStringBuilder();
    private final String mockMongoData = "mockMongoData";
    private final String DEVELOPER_KEY = "developer";

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MockMvc mockMvc;

    private ApiCaller apiCaller;

    @BeforeEach
    public void setUp() {
        mongoTemplate.getDb().drop();
        apiCaller = new ApiCaller(mockMvc);
    }

    @Test
    public void shouldReturnOnlyMatchingRecord() throws Exception {
        //given
        insertThreeValueRecordToMockMongoDataCollection(DEVELOPER_KEY, 100, true);
        insertThreeValueRecordToMockMongoDataCollection("notMatchingRecord", 200, false);

        //when
        String queryString = builder.matchQuery(List.of(Tuple.of("name", DEVELOPER_KEY), Tuple.of("age", 100), Tuple.of("finished", true)));
        java.util.List<Map<String, Object>> docs = apiCaller.callSearchApi(queryString, mockMongoData, false);

        //then
        assertThat(docs.size(), is(1));
        assertThat(docs.get(0).get("name"), is(DEVELOPER_KEY));
        assertThat(docs.get(0).get("age"), is(100));
        assertThat(docs.get(0).get("finished"), is(true));
        assertThat(docs.get(0).size(), is(3));
    }

    @Test
    public void shouldReturnAllThreeMatchingRecords() throws Exception {
        //given
        insertExampleData();

        //when
        String queryString = builder.matchQuery(List.of(Tuple.of("name", DEVELOPER_KEY)));
        java.util.List<Map<String, Object>> docs = apiCaller.callSearchApi(queryString, mockMongoData, false);

        //then
        assertThat(docs.size(), is(3));
        docs.forEach(doc -> assertThat(doc.get("name"), is(DEVELOPER_KEY)));
    }

    @Test
    public void shouldReturnCorrectAverage() throws Exception {
        //given
        insertExampleData();

        //when
        String queryString = ElasticsearchQueryProvider.loadElasticQueryFromFile("aggregation_avg_match_query.json");
        java.util.List<Map<String, Object>> docs = apiCaller.callSearchApi(queryString, mockMongoData, false);

        //then
        double correctAverage = (100.0 + 200.0 + 300.0) / 3;
        docs.forEach(doc -> assertThat(doc.get("_avg"), is(correctAverage)));
    }

    @Test
    public void shouldReturnCorrectMin() throws Exception {
        //given
        insertExampleData();

        //when
        String queryString = ElasticsearchQueryProvider.loadElasticQueryFromFile("aggregation_min_match_query.json");
        java.util.List<Map<String, Object>> docs = apiCaller.callSearchApi(queryString, mockMongoData, false);

        //then
        int correctMin = 100;
        docs.forEach(doc -> assertThat(doc.get("_min"), is(correctMin)));
    }

    @Test
    public void shouldReturnCorrectMax() throws Exception {
        //given
        insertExampleData();

        //when
        String queryString = ElasticsearchQueryProvider.loadElasticQueryFromFile("aggregation_max_match_query.json");
        java.util.List<Map<String, Object>> docs = apiCaller.callSearchApi(queryString, mockMongoData, false);

        //then
        int correctMax = 300;
        docs.forEach(doc -> assertThat(doc.get("_max"), is(correctMax)));
    }

    @Test
    public void shouldReturnCorrectSum() throws Exception {
        //given
        insertExampleData();

        //when
        String queryString = ElasticsearchQueryProvider.loadElasticQueryFromFile("aggregation_sum_match_query.json");
        java.util.List<Map<String, Object>> docs = apiCaller.callSearchApi(queryString, mockMongoData, false);

        //then
        int correctSum = 600;
        docs.forEach(doc -> assertThat(doc.get("_sum"), is(correctSum)));
    }

    @Test
    public void shouldReturnCorrectCount() throws Exception {
        //given
        insertExampleData();

        //when
        String queryString = ElasticsearchQueryProvider.loadElasticQueryFromFile("aggregation_count_match_query.json");
        java.util.List<Map<String, Object>> docs = apiCaller.callSearchApi(queryString, mockMongoData, false);

        //then
        int correctCount = 3;
        docs.forEach(doc -> assertThat(doc.get("_count"), is(3)));
    }

    private void insertExampleData() {
        insertThreeValueRecordToMockMongoDataCollection(DEVELOPER_KEY, 100, true);
        insertThreeValueRecordToMockMongoDataCollection(DEVELOPER_KEY, 200, false);
        insertThreeValueRecordToMockMongoDataCollection(DEVELOPER_KEY, 300, true);
    }

    private void insertThreeValueRecordToMockMongoDataCollection(String stringValue, Integer intValue, Boolean boolValue) {
        insertData(Map.of("name", stringValue, "age", intValue, "finished", boolValue), mockMongoData);
    }

    private void insertData(Map<String, Object> data, String collectionName) {
        Document document = new Document(data);
        mongoTemplate.insert(document, collectionName);
    }
}