package org.example.urlshortner_task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "urls")
@Entity
public class UrlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    @Column(name = "shortened_url")
    public String shortenedUrl;

    public boolean clicked;

    public UrlEntity(String shortenedUrl, boolean clicked) {
        this.shortenedUrl = shortenedUrl;
        this.clicked = false;
    }
}
