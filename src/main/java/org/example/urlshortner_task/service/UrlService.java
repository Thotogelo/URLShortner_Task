package org.example.urlshortner_task.service;

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

    public String encodeUrl(String longUrl) {

        Optional<UrlEntity> existingShortUrl = urlRepository.findByShortUrl(longUrl);
        if (existingShortUrl.isPresent()) {
            markAsClicked(existingShortUrl.get());
            return existingShortUrl.get().getLongUrl();
        }

        Optional<UrlEntity> existingLongUrl = urlRepository.findByLongUrl(longUrl);
        if (existingLongUrl.isPresent()) {
            return existingLongUrl.get().getShortUrl();
        }

        String shortUrl = generateShortUrl(longUrl);
        UrlEntity url = new UrlEntity();
        url.setLongUrl(longUrl);
        url.setShortUrl(shortUrl);
        url.setClicked(false);
        urlRepository.save(url);
        return shortUrl;
    }

    public Optional<UrlEntity> decodeUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl);
    }

    public void markAsClicked(UrlEntity url) {
        url.setClicked(true);
        urlRepository.save(url);
    }

    private String generateShortUrl(String longUrl) {
        return Base64.getUrlEncoder().encodeToString(longUrl.getBytes(StandardCharsets.UTF_8)).substring(0, 7);
    }
}

