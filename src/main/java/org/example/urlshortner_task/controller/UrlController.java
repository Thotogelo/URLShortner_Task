package org.example.urlshortner_task.controller;


import jakarta.validation.Valid;
import org.example.urlshortner_task.entity.UrlDTO;
import org.example.urlshortner_task.entity.UrlEntity;
import org.example.urlshortner_task.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.Objects;

@RestController
@RequestMapping("/url")
public class UrlController {

    @Autowired
    private UrlRepository urlRepository;

//    public UrlController(UrlRepository urlRepository) {
//        this.urlRepository = urlRepository;
//    }


    @PostMapping
    public ResponseEntity<Object> saveUrl(@Valid @RequestBody UrlDTO urlLong) {
        if (Objects.isNull(urlLong)) {
            return ResponseEntity.badRequest().body("URL is required");
        }

        String UrlEncodedShort = Base64.getUrlEncoder().encodeToString(urlLong.toString().getBytes());

        //Already exists
        String decoded = Base64.getUrlDecoder().decode(UrlEncodedShort.getBytes()).toString();
        if (urlRepository.existsByShortenedUrl(UrlEncodedShort)) {
            return new ResponseEntity<>(decoded, HttpStatus.CREATED);
        } else {
            //Given a short url, that doesn't exist
            if (urlLong.toString().equals(decoded)) {
                UrlEntity updateUrl = urlRepository.findByShortened_url(UrlEncodedShort).orElse(null);

                if (Objects.nonNull(updateUrl)) {
                    updateUrl.setClicked(true);
                    urlRepository.save(updateUrl);
                }
                return new ResponseEntity<>(decoded, HttpStatus.OK);
            }
        }

        UrlEntity newUrl = new UrlEntity(UrlEncodedShort, false);
        return ResponseEntity.ok(urlRepository.save(newUrl));
    }

}
