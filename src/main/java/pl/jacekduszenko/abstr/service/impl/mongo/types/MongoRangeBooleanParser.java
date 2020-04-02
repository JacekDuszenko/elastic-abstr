package pl.jacekduszenko.abstr.service.impl.mongo.types;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.SneakyThrows;
import pl.jacekduszenko.abstr.model.exception.TranslationException;

public class MongoRangeBooleanParser implements MongoRangeParser<Boolean> {
    public static final String booleanTrue = "true";
    public static final String booleanFalse = "false";

    @Override
    @SneakyThrows
    public Tuple2<Boolean, Boolean> apply(Tuple2<String, String> entryArguments) {
        if (!(isBoolean(entryArguments._1) && isBoolean(entryArguments._2))) {
            throw new TranslationException("could not parse left or right range argument to boolean");
        }
        return Tuple.of(parseBool(entryArguments._1), parseBool(entryArguments._2));
    }

    private boolean isBoolean(String s) {
        return booleanTrue.equals(s) || booleanFalse.equals(s);
    }

    @SneakyThrows
    private boolean parseBool(String s) {
        if (booleanTrue.equals(s)) {
            return Boolean.TRUE;
        } else if (booleanFalse.equals(s)) {
            return Boolean.FALSE;
        }
        throw new TranslationException("boolean value is none of true or false");
    }
}
