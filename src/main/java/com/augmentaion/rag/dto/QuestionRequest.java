package com.augmentaion.rag.dto;

import jakarta.validation.constraints.NotBlank;

public record QuestionRequest(
        @NotBlank String sessionId,
        @NotBlank String question
) {
}
