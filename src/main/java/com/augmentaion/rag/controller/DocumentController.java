package com.augmentaion.rag.controller;

import com.augmentaion.rag.dto.DocumentResponse;
import com.augmentaion.rag.dto.DocumentUploadResponse;
import com.augmentaion.rag.service.DocumentIngestionService;
import com.augmentaion.rag.service.DocumentMetadataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentIngestionService ingestionService;

    private final DocumentMetadataService documentMetadataService;

    @GetMapping
    public List<DocumentResponse> getAllDocuments() {

        return documentMetadataService.getAllDocuments();
    }

    @GetMapping("/{documentId}")
    public DocumentResponse getDocument(
            @PathVariable UUID documentId) {

        return documentMetadataService.getDocument(documentId);
    }

    @PostMapping("/upload")
    public DocumentUploadResponse upload(
            @RequestParam("file")
            MultipartFile file) {

        ingestionService.ingest(file);

        return new DocumentUploadResponse("Document stored successfully");
    }


}
