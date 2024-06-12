package org.example.urlshortner_task.repository;

import org.example.urlshortner_task.entity.UrlEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UrlRepository extends CrudRepository<UrlEntity, UUID> {
    boolean existsByShortenedUrl(String shortened_url);

    @Query("select u from UrlEntity u where u.shortenedUrl = ?1")
    Optional<UrlEntity> findByShortened_url(String shortened_url);

}