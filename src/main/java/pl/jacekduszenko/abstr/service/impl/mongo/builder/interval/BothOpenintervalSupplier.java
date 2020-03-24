package pl.jacekduszenko.abstr.service.impl.mongo.builder.interval;

import io.vavr.Tuple2;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import pl.jacekduszenko.abstr.service.impl.mongo.builder.MongoQueryBuilder;

import java.util.function.Consumer;

public class BothOpenintervalSupplier extends IntervalSupplier {

    public BothOpenintervalSupplier(MongoQueryBuilder builder, String field, String lowerTermValue, String upperTermValue) {
        super(builder, field, lowerTermValue, upperTermValue);
    }

    @Override
    public Consumer<Tuple2> createIntervalConsumer(Query mongoQuery, String field) {
        return (tpl) -> mongoQuery.addCriteria(Criteria.where(field).lt(tpl._2).gt(tpl._1));
    }
}
