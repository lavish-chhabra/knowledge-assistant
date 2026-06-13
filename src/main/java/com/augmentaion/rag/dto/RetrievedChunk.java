package com.augmentaion.rag.dto;

public record RetrievedChunk(
        String content,
        String fileName,
        int chunkNumber,
        double score
    )    {}
