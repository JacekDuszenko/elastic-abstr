package pl.jacekduszenko.abstr.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RequiredArgsConstructor
public class ApiCaller {

    private final MockMvc mockMvc;

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> callSearchApi(String searchQuery, String collectionName, Boolean verbose) throws Exception {
        MvcResult result = this.mockMvc.perform(get(String.format("/api/v1/search/%s", collectionName))
                .content(searchQuery)
                .param("verbose", verbose.toString()))
                .andReturn();

        return new ObjectMapper().readValue(result.getResponse().getContentAsString(), List.class);
    }
}
