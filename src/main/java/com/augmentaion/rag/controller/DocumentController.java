package com.augmentaion.rag.controller;

import com.augmentaion.rag.dto.DocumentUploadResponse;
import com.augmentaion.rag.service.DocumentIngestionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentIngestionService ingestionService;

    public DocumentController(
            DocumentIngestionService ingestionService) {

        this.ingestionService = ingestionService;
    }

    @PostMapping("/upload")
    public DocumentUploadResponse upload(
            @RequestParam("file")
            MultipartFile file) {

        ingestionService.ingest(file);

        return new DocumentUploadResponse("Document stored successfully");
    }
}
