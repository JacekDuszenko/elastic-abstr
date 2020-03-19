package pl.jacekduszenko.abstr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jacekduszenko.abstr.model.QueryResult;
import pl.jacekduszenko.abstr.service.QueryService;

@RestController
@RequestMapping("/api/v1")
public class QueryController {

    private final QueryService queryService;

    @Autowired
    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @RequestMapping("/search")
    QueryResult searchForQuery(@RequestBody String elasticQuery) {
        return queryService.translateQuery(elasticQuery);
    }
}
