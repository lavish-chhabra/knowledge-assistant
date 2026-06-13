package com.augmentaion.rag.service;

import com.augmentaion.rag.dto.DocumentChunk;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VectorStoreService {

    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<TextSegment> embeddingStore;

    public void storeChunk(DocumentChunk chunk) {
        // Convert String to LangChain4j TextSegment
        // its more like a chunk object for langchain4j which contains metadata as well

        Metadata metadata = new Metadata();

        metadata.put("documentId", chunk.documentId().toString());

        metadata.put("fileName", chunk.fileName());

        metadata.put("chunkNumber", String.valueOf(chunk.chunkNumber()));

        TextSegment segment = TextSegment.from(chunk.content(), metadata);

        // Generate embedding vector
        Embedding embedding = embeddingModel
                .embed(segment)
                .content();

        // Store vector + text in Qdrant
        embeddingStore.add(embedding, segment);
    }
}
