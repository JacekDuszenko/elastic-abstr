package pl.jacekduszenko.abstr.service.impl.mongo.builder.interval;

import io.vavr.Tuple2;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Query;
import pl.jacekduszenko.abstr.service.impl.mongo.builder.MongoQueryBuilder;

import java.util.function.Consumer;
import java.util.function.Supplier;

@RequiredArgsConstructor
public abstract class IntervalSupplier implements Supplier<Void> {
    protected final MongoQueryBuilder builder;
    protected final String field;
    protected final String lowerTermValue;
    protected final String upperTermValue;

    @Override
    public Void get() {
        builder.withRangeClause(field, lowerTermValue, upperTermValue, this);
        return null;
    }

    public abstract Consumer<Tuple2> createIntervalConsumer(Query mongoQuery, String field);
}
