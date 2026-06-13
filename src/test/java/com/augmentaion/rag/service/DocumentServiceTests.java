package com.augmentaion.rag.service;

import com.augmentaion.rag.config.KnowledgeAssistantProperties;
import com.augmentaion.rag.dto.DocumentChunk;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DocumentServiceTests {

    @Test
    void chunkTextUsesConfiguredOverlap() {

        KnowledgeAssistantProperties properties = new KnowledgeAssistantProperties();
        properties.getDocuments().setChunkSize(10);
        properties.getDocuments().setChunkOverlap(2);

        DocumentService service = new DocumentService(properties);

        List<DocumentChunk> chunks = service.chunkText(UUID.randomUUID(),"xya","abcdefghijklmnopqrst");

        assertThat(chunks)
                .extracting(DocumentChunk::content)
                .containsExactly("abcdefghij", "ijklmnopqr", "qrst");
    }

    @Test
    void extractTextRejectsNonPdfFiles() {

        DocumentService service =
                new DocumentService(new KnowledgeAssistantProperties());

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "notes.txt",
                "text/plain",
                "hello".getBytes(StandardCharsets.UTF_8)
        );
    }
}
