package com.augmentaion.rag.service;

import com.augmentaion.rag.dto.DocumentChunk;
import com.augmentaion.rag.entity.Document;
import com.augmentaion.rag.enums.DocumentStatus;
import com.augmentaion.rag.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentIngestionService {

    private final DocumentRepository documentRepository;
    private final DocumentService documentService;
    private final VectorStoreService vectorStoreService;

    public void ingest(MultipartFile file) {

        Document document = Document.builder()
                .fileName(file.getOriginalFilename())
                .uploadedBy("Lavish")
                .status(DocumentStatus.PROCESSING)
                .uploadedAt(LocalDateTime.now())
                .fileSize(file.getSize())
                .contentType(file.getContentType())
                .build();

        document = documentRepository.save(document);

        try {

            // Extract text
            String text =
                    documentService.extractText(file);

            // Chunk text
            List<DocumentChunk> chunks =
                    documentService.chunkText(document.getId(), document.getFileName(), text);

            if (chunks.isEmpty()) {

                throw new IllegalArgumentException(
                        "PDF does not contain extractable text");
            }

            // Store embeddings
            for (DocumentChunk chunk : chunks) {

                vectorStoreService.storeChunk(chunk);
            }

            // Update metadata
            document.setChunkCount(
                    (long) chunks.size());

            document.setStatus(
                    DocumentStatus.COMPLETED);

            documentRepository.save(document);

        } catch (Exception ex) {

            document.setStatus(
                    DocumentStatus.FAILED);

            documentRepository.save(document);

            throw ex;
        }
    }
}