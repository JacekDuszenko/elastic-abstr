package pl.jacekduszenko.abstr.integration.mongo;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MongoDataFeed implements ApplicationRunner {

    private final static String COLLECTION_NAME = "mongo_bnyabstr";
    private final static String CSV_DATA_RESOURCE_NAME = "static/example_mongo_data.csv";
    private final static String CSV_SEPARATOR = ";";
    private final static long ONE_ELEMENT = 1L;

    private final MongoTemplate mongoTemplate;

    @Override
    public void run(ApplicationArguments args) {
        mongoTemplate.getCollection(COLLECTION_NAME).insertMany(loadDataFromCsv());
        System.out.printf("lolz");
    }

    @SneakyThrows
    private List<Document> loadDataFromCsv() {
        Path csvFilePath = Paths.get(getClass().getClassLoader().getResource(CSV_DATA_RESOURCE_NAME).toURI());
        Supplier<Stream<String[]>> wordStreamSupplier = () -> getWordsStream(csvFilePath);
        String[] keywords = wordStreamSupplier.get().findFirst().orElseGet(() -> new String[]{});

        return wordStreamSupplier.get().skip(ONE_ELEMENT)
                .map(array -> convertToDocument(keywords, array))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private Stream<String[]> getWordsStream(Path csvFilePath) {
        return Files.readAllLines(csvFilePath)
                .stream()
                .map(line -> line.split(CSV_SEPARATOR));
    }

    private Document convertToDocument(String[] keywords, String[] array) {
        Document doc = new Document();
        for (int i = 0; i < array.length; ++i) {
            doc.put(keywords[i], array[i]);
        }
        return doc;
    }
}