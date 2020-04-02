package pl.jacekduszenko.abstr.service.impl.mongo.types;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class MongoRangeDateParserTest {

    private MongoRangeDateParser mongoRangeDateParser;
    public static final String validLeftDate = "2020-03-03t00:00:00.000z";
    public static final long validLeftDateEpochTime = 1583190000000L;
    public static final String validRightDate = "2020-03-06t00:00:00.000z";
    public static final long validRightDateEpochTime = 1583449200000L;

    @BeforeEach
    void setUp() {
        mongoRangeDateParser = new MongoRangeDateParser();
    }

    @Test
    void shouldParseDateCorrectly() {
        //given
        Tuple2<String, String> validDates = Tuple.of(validLeftDate, validRightDate);

        //when
        Tuple2<Date, Date> parsedDates = mongoRangeDateParser.apply(validDates);

        //then
        assertLeftDate(parsedDates._1);
        assertRightDate(parsedDates._2);
    }

    private void assertLeftDate(Date date) {
        assertThat(date.getTime(), is(validLeftDateEpochTime));
    }

    private void assertRightDate(Date date) {
        assertThat(date.getTime(), is(validRightDateEpochTime));
    }
}