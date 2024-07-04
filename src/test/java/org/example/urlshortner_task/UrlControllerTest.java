package org.example.urlshortner_task;

import org.example.urlshortner_task.entity.RequestUrl;
import org.example.urlshortner_task.service.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springdoc.webmvc.core.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UrlControllerTest {

    @MockBean
    private UrlService urlService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RequestService requestBuilder;

    private RequestUrl requestUrl;
    private final String postUrl = "http://localhost:8080/api/urls/shorten";

    @BeforeEach
    void setUp() {
        requestUrl = new RequestUrl("https://chatgpt.com/c/fdfdf-07db-4d98-a61c-3ce2319a8f73");
    }

//    TODO Parameterize the method
    @Test
    void givenExistingLongUrlReturnShortUrl() throws Exception {
        when(urlService.shortenUrl(requestUrl)).thenReturn("aHR0cHM");

        mockMvc.perform(post(postUrl)
                        .content("{\"url\":\"https://chatgpt.com/c/fdfdf-07db-4d98-a61c-3ce2319a8f73\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("aHR0cHM"));
    }
}
