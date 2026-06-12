package com.augmentaion.rag.service;

import com.augmentaion.rag.dto.DocumentChunk;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DocumentIngestionService {

    private final DocumentService documentService;
    private final VectorStoreService vectorStoreService;

    public DocumentIngestionService(
            DocumentService documentService,
            VectorStoreService vectorStoreService) {

        this.documentService = documentService;
        this.vectorStoreService = vectorStoreService;
    }

    public void ingest(MultipartFile file) {

        // Step 1
        String text =
                documentService.extractText(file);

        // Step 2
        List<DocumentChunk> chunks =
                documentService.chunkText(text);

        if (chunks.isEmpty()) {
            throw new IllegalArgumentException("PDF does not contain extractable text");
        }

        // Step 3
        for (DocumentChunk chunk : chunks) {

            vectorStoreService.storeChunk(
                    chunk.content()
            );
        }
    }
}
