package com.augmentaion.rag.service;

import com.augmentaion.rag.config.KnowledgeAssistantProperties;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetrievalService {

    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<TextSegment> embeddingStore;
    private final KnowledgeAssistantProperties properties;

    public RetrievalService(
            EmbeddingModel embeddingModel,
            EmbeddingStore<TextSegment> embeddingStore,
            KnowledgeAssistantProperties properties) {

        this.embeddingModel = embeddingModel;
        this.embeddingStore = embeddingStore;
        this.properties = properties;
    }

    public List<String> search(String question) {

        // step 1 -> embed the question/input text
        Embedding queryEmbedding = embeddingModel.embed(question).content();

        // step 2 -> prepare request to search in qdrant
        EmbeddingSearchRequest request = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
                .maxResults(properties.getRetrieval().getMaxResults())
                .build();

        // step 3 -> search in qdrant
        EmbeddingSearchResult<TextSegment> result = embeddingStore.search(request);

        // step 4
        return result.matches()
                .stream()
                .map(match -> match.embedded().text())
                .toList();


    }
}
