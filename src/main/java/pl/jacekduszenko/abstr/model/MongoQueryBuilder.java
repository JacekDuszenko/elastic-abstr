package pl.jacekduszenko.abstr.model;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class MongoQueryBuilder {
    private final Query mongoQuery;

    public static MongoQueryBuilder create() {
        return new MongoQueryBuilder();
    }

    private MongoQueryBuilder() {
        this.mongoQuery = new Query();
    }

    public void withIsClause(String field, String value) {
        mongoQuery.addCriteria(Criteria.where(field).regex("^" + value + "$", "i"));
    }

    public Query build() {
        return mongoQuery;
    }
}
