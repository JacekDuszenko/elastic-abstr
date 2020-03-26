package pl.jacekduszenko.abstr.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
public class MongoMockData {
    private final String name;
    private final int age;
    private final boolean finished;
}
