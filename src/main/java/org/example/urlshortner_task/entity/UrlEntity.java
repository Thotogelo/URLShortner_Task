package org.example.urlshortner_task.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "urls")
public class UrlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "original_url")
    private String longUrl;

    @Column(name = "shortened_url")
    private String shortUrl;

    @Column(name = "clicked")
    private int clicked;
}
