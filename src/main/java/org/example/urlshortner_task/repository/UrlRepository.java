package org.example.urlshortner_task.repository;

import org.example.urlshortner_task.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, UUID> {
    Optional<UrlEntity> findByLongUrl(String longUrl);

    Optional<UrlEntity> findByShortUrl(String shortUrl);
}