package pl.jacekduszenko.abstr.service.impl.mongo.types;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.SneakyThrows;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MongoRangeDateParser implements MongoRangeParser<Date> {
    public static final DateFormat elasticsearchDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Tuple2<Date, Date> apply(Tuple2<String, String> entryArguments) {
        return Tuple.of(fromString(entryArguments._1), fromString(entryArguments._2));
    }

    @SneakyThrows
    private Date fromString(String s) {
        return elasticsearchDateFormat.parse(s);
    }
}
