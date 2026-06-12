package com.augmentaion.rag.service;

import com.augmentaion.rag.config.KnowledgeAssistantProperties;
import com.augmentaion.rag.dto.DocumentChunk;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentService {

    private static final String PDF_CONTENT_TYPE = "application/pdf";

    private final KnowledgeAssistantProperties properties;

    public DocumentService(KnowledgeAssistantProperties properties) {
        this.properties = properties;
    }

    public String extractText(MultipartFile file) {

        validatePdf(file);

        try (PDDocument document =
                     PDDocument.load(file.getInputStream())) {

            PDFTextStripper stripper =
                    new PDFTextStripper();

            return stripper.getText(document);

        } catch (IOException e) {

            throw new IllegalArgumentException(
                    "Failed to process PDF", e);
        }
    }

    public List<DocumentChunk> chunkText(String text) {

        List<DocumentChunk> chunkList = new ArrayList<>();
        int chunkSize = properties.getDocuments().getChunkSize();
        int overlap = properties.getDocuments().getChunkOverlap();

        if (text == null || text.isBlank()) {
            return chunkList;
        }

        if (overlap >= chunkSize) {
            throw new IllegalStateException(
                    "Document chunk overlap must be smaller than chunk size");
        }

        int step = chunkSize - overlap;

        for(int start=0; start<text.length(); start+=step) {

            int end = Math.min(text.length(),start+chunkSize);
            String content = text.substring(start,end);

            chunkList.add(new DocumentChunk(UUID.randomUUID().toString(),content));
        }
        return chunkList;
    }

    private void validatePdf(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("A non-empty PDF file is required");
        }

        if (file.getSize() > properties.getDocuments().getMaxUploadSize().toBytes()) {
            throw new IllegalArgumentException("PDF exceeds maximum upload size");
        }

        if (!PDF_CONTENT_TYPE.equalsIgnoreCase(file.getContentType())) {
            throw new IllegalArgumentException("Only PDF files are supported");
        }
    }
}
