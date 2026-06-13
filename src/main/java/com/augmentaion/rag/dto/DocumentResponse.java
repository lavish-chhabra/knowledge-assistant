package com.augmentaion.rag.dto;

import com.augmentaion.rag.enums.DocumentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record DocumentResponse(
        UUID id,
        String fileName,
        String uploadedBy,
        DocumentStatus status,
        Long chunkCount,
        Long fileSize,
        String contentType,
        LocalDateTime uploadedAt
) {
}
