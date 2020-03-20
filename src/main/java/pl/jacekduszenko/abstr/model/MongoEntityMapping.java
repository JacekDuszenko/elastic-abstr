package pl.jacekduszenko.abstr.model;

import java.util.Map;

public class MongoEntityMapping {
    private final static String MONGO_MOCK_DATA_COLLECTION_NAME = "mongoMockData";

    public final static Map<String, Class> mapping = Map.of(
            MONGO_MOCK_DATA_COLLECTION_NAME, MongoMockData.class
    );
}
