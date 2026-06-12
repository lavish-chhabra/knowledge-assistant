package com.augmentaion.rag.service;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.springframework.stereotype.Service;

@Service
public class VectorStoreService {

    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<TextSegment> embeddingStore;

    public VectorStoreService(
            EmbeddingModel embeddingModel,
            EmbeddingStore<TextSegment> embeddingStore) {

        this.embeddingModel = embeddingModel;
        this.embeddingStore = embeddingStore;
    }

    public void storeChunk(String content) {
        // Convert String to LangChain4j TextSegment
        // its more like a chunk object for langchain4j which contains metadata as well
        TextSegment segment = TextSegment.from(content);

        // Generate embedding vector
        Embedding embedding = embeddingModel
                .embed(segment)
                .content();

        // Store vector + text in Qdrant
        embeddingStore.add(
                embedding,
                segment
        );
    }
}
