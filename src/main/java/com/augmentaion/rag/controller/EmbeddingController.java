package com.augmentaion.rag.controller;

import com.augmentaion.rag.dto.DocumentChunk;
import com.augmentaion.rag.service.DocumentService;
import com.augmentaion.rag.service.EmbeddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class EmbeddingController {

    private final EmbeddingService embeddingService;
    private final DocumentService documentService;

    @PostMapping("/embed")
    public float[] embed(
            @RequestParam("file") MultipartFile file) {

        String content = documentService.extractText(file);
        List<DocumentChunk> documentChunkList = documentService.chunkText(UUID.randomUUID(), file.getName(), content);
        if (documentChunkList.isEmpty()) {
            throw new IllegalArgumentException("PDF does not contain extractable text");
        }

        return embeddingService.embed(documentChunkList.getFirst().content());
    }
}
