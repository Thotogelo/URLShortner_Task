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

    public static final String shortUrl = "aHR0cHM";
    public static final String longUrl = "https://chatgpt.com/c/fdfdf-07db-4d98-a61c-3ce2319a8f73";
    private static final String postUrl = "http://localhost:8080/api/urls/shorten";

    @MockBean
    private UrlService urlService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RequestService requestBuilder;

    private RequestUrl requestUrl;

    @BeforeEach
    void setUp() {
        requestUrl = new RequestUrl(longUrl);
    }

    //    TODO Parameterize the method
    @Test
    void shouldReturnShortUrlForExistingLongUrl() throws Exception {
        when(urlService.resolveOrShortenUrl(requestUrl)).thenReturn(shortUrl);

        mockMvc.perform(post(postUrl)
                        .content("""
                                {"url":"https://chatgpt.com/c/fdfdf-07db-4d98-a61c-3ce2319a8f73"}
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(shortUrl));
    }

//    TODO
    @Test
    void shouldReturnLongUrlForGivenShortUrl() throws Exception {
//        Pass short url, then return long url
        requestUrl = new RequestUrl(shortUrl);
        when(urlService.resolveOrShortenUrl(requestUrl)).thenReturn(longUrl);

        mockMvc.perform(post(postUrl)
                        .content("""
                                {"url":"aHR0cHM"}
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(longUrl));
    }
}
