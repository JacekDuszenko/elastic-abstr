package pl.jacekduszenko.abstr.service.impl.mongo.types;

import io.vavr.Tuple2;

public class MongoRangeIdentityParser implements MongoRangeParser<String> {
    @Override
    public Tuple2<String, String> apply(Tuple2<String, String> arguments) {
        return arguments;
    }
}
