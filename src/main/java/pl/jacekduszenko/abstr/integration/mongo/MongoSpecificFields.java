package pl.jacekduszenko.abstr.integration.mongo;

import java.util.Set;

public class MongoSpecificFields {

    private static final String MONGO_ID_METADATA = "_id";
    private static final String MONGO_CLASS_METADATA = "_class";

    public static final Set<String> mongoSpecificFields = Set.of(MONGO_ID_METADATA, MONGO_CLASS_METADATA);
}
