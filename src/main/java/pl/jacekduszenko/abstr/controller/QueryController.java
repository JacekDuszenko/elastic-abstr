package pl.jacekduszenko.abstr.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jacekduszenko.abstr.model.QueryResult;
import pl.jacekduszenko.abstr.model.exception.TranslationException;
import pl.jacekduszenko.abstr.model.exception.VisitorCreationException;
import pl.jacekduszenko.abstr.service.QueryService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QueryController {

    private final QueryService queryService;

    @GetMapping("/search/{collection}")
    ResponseEntity searchForQuery(@RequestBody String elasticQuery, @PathVariable String collection) throws IOException, VisitorCreationException, TranslationException {
        return ResponseEntity.ok(queryService.search(elasticQuery, collection));
    }
}
