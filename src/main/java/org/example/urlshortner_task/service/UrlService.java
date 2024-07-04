package org.example.urlshortner_task.service;

import org.example.urlshortner_task.entity.RequestUrl;
import org.example.urlshortner_task.entity.UrlEntity;
import org.example.urlshortner_task.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UrlService {

    private final Logger logger = Logger.getLogger(UrlService.class.getName());
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String resolveOrShortenUrl(RequestUrl requestUrl) {

        Optional<UrlEntity> existingShortUrl = urlRepository.findByShortUrl(requestUrl.url());
        if (existingShortUrl.isPresent()) {
            markAsClicked(existingShortUrl.get());
            return existingShortUrl.get().getLongUrl();
        }

        Optional<UrlEntity> existingLongUrl = urlRepository.findByLongUrl(requestUrl.url());
        if (existingLongUrl.isPresent()) {
            return existingLongUrl.get().getShortUrl();
        }

        return saveUrl(requestUrl.url());
    }

    private String saveUrl(String requestUrl) {
        String shortUrl = generateShortUrl(requestUrl);
        UrlEntity newUrl = new UrlEntity();
        newUrl.setLongUrl(requestUrl);
        newUrl.setShortUrl(shortUrl);
        newUrl.setClicked(newUrl.getClicked() + 1);
        urlRepository.save(newUrl);
        return shortUrl;
    }

    private void markAsClicked(UrlEntity url) {
        url.setClicked(url.getClicked() + 1);
        urlRepository.save(url);
    }

    private String generateShortUrl(String longUrl) {
        return Base64.getUrlEncoder().encodeToString(longUrl.getBytes(StandardCharsets.UTF_8)).substring(0, 7);
    }
}

