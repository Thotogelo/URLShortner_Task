package org.example.urlshortner_task.controller;


import org.example.urlshortner_task.entity.UrlDTO;
import org.example.urlshortner_task.service.UrlService;
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
    public String shortenUrl(@RequestBody UrlDTO longUrl) {
        return urlService.encodeUrl(longUrl.longUrl());
    }
}