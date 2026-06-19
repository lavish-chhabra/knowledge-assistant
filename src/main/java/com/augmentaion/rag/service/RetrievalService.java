package com.augmentaion.rag.service;

import com.augmentaion.rag.config.KnowledgeAssistantProperties;
import com.augmentaion.rag.dto.RetrievedChunk;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RetrievalService {

    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<TextSegment> embeddingStore;
    private final KnowledgeAssistantProperties properties;

    public List<RetrievedChunk> search(String question) {

        System.out.println(properties.getGemini().getApiKey());
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
                .map(match -> {

                    TextSegment segment =
                            match.embedded();

                    return new RetrievedChunk(
                            segment.text(),
                            segment.metadata().getString("fileName"),
                            Integer.parseInt(Objects.requireNonNull(segment.metadata().getString("chunkNumber"))),
                            match.score()
                    );
                })
                .toList();


    }
}
