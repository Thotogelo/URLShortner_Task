package org.example.urlshortner_task.controller;


import jakarta.validation.Valid;
import org.example.urlshortner_task.entity.RequestUrl;
import org.example.urlshortner_task.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/urls")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<String> saveUrl(@Valid @RequestBody RequestUrl requestUrl) {
        return ResponseEntity.ok(urlService.resolveOrShortenUrl(requestUrl));
    }
}