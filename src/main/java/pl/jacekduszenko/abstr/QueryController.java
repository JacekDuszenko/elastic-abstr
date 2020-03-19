package pl.jacekduszenko.abstr;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jacekduszenko.abstr.model.QueryResult;

@RestController
@RequestMapping("/api/v1")
public class QueryController {

    @RequestMapping("/search")
    ResponseEntity<QueryResult> searchForQuery(@RequestBody String elasticQuery) {

    }
}
