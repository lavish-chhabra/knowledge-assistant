package com.augmentaion.rag.dto;

import java.util.UUID;

public record DocumentChunk(
        String chunkId,
        UUID documentId,
        String fileName,
        Integer chunkNumber,
        String content
) {}
