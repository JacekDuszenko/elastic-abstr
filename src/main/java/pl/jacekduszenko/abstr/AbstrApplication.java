package pl.jacekduszenko.abstr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories
public class AbstrApplication {

	public static void main(String[] args) {
		SpringApplication.run(AbstrApplication.class, args);
	}

}
