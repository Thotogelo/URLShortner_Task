package org.example.urlshortner_task.controller;


import org.example.urlshortner_task.entity.UrlDTO;
import org.example.urlshortner_task.entity.UrlEntity;
import org.example.urlshortner_task.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/urls")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public String shortenUrl(@RequestBody UrlDTO longUrl) {
        return urlService.encodeUrl(longUrl.longUrl());
    }

    @GetMapping("{shortUrl}")
    public String redirectUrl(@PathVariable String shortUrl) {
        Optional<UrlEntity> urlOptional = urlService.decodeUrl(shortUrl);
        if (urlOptional.isPresent()) {
            UrlEntity url = urlOptional.get();
            urlService.markAsClicked(url);
            return url.getLongUrl();
        }
        return "URL not found";
    }
}