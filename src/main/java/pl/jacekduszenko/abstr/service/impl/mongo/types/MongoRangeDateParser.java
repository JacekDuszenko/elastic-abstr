package pl.jacekduszenko.abstr.service.impl.mongo.types;

import io.vavr.Tuple2;

import java.util.Date;

public class MongoRangeDateParser implements MongoRangeParser<Date> {
    @Override
    public Tuple2<Date, Date> apply(Tuple2<String, String> stringStringTuple2) {
        return null;
    }
}
