package pl.jacekduszenko.abstr.integration;

import io.vavr.collection.Stream;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import pl.jacekduszenko.abstr.model.MongoMockData;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MongoDataFeed implements ApplicationRunner {

    private final MongoTemplate mongoTemplate;

    @Override
    public void run(ApplicationArguments args) {
        createMockData(50).forEach((mongoTemplate::insert));
    }

    private List<MongoMockData> createMockData(int quantity) {
        return Stream.continually(MongoDataFeed::random).take(quantity).toJavaList();
    }

    private static MongoMockData random() {
        return new MongoMockData(RandomStringUtils.random(10, true, false),
                RandomUtils.nextInt(20, 100),
                RandomUtils.nextBoolean());
    }
}