package pl.jacekduszenko.abstr.data;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Slf4j
public class ElasticsearchQueryProvider {
    private static final String BLANK = "";
    private static ClassLoader classLoader = ElasticsearchQueryProvider.class.getClassLoader();

    public static String loadElasticQueryFromFile(String filename) {
        try {
            return loadFileToString(filename);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return BLANK;
        }
    }

    private static String loadFileToString(String filename) throws URISyntaxException, IOException {
        Path p = Paths.get(Objects.requireNonNull(classLoader.getResource(filename)).toURI());
        return new String(Files.readAllBytes(p));
    }
}