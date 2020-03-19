package pl.jacekduszenko.abstr;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jacekduszenko.abstr.model.QueryResult;
import pl.jacekduszenko.abstr.service.QueryService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QueryController {

    private final QueryService queryService;

    @GetMapping("/search")
    QueryResult searchForQuery(@RequestBody String elasticQuery) throws IOException {
        return queryService.translateQuery(elasticQuery);
    }
}
