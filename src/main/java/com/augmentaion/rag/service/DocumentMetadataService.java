package com.augmentaion.rag.service;

import com.augmentaion.rag.dto.DocumentResponse;
import com.augmentaion.rag.entity.Document;
import com.augmentaion.rag.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentMetadataService {

    private final DocumentRepository documentRepository;

    public List<DocumentResponse> getAllDocuments() {

        return documentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public DocumentResponse getDocument(UUID documentId) {

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Document not found: " + documentId));

        return mapToResponse(document);
    }

    private DocumentResponse mapToResponse(Document document) {

        return new DocumentResponse(
                document.getId(),
                document.getFileName(),
                document.getUploadedBy(),
                document.getStatus(),
                document.getChunkCount(),
                document.getFileSize(),
                document.getContentType(),
                document.getUploadedAt()
        );
    }
}
