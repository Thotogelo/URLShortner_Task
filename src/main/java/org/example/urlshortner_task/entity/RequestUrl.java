package org.example.urlshortner_task.entity;

import jakarta.validation.constraints.NotBlank;

public record RequestUrl(@NotBlank String url) {
}
