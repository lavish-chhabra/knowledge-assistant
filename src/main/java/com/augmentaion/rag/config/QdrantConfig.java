package com.augmentaion.rag.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QdrantConfig {

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore(KnowledgeAssistantProperties properties) {

        return QdrantEmbeddingStore.builder()
                .host(properties.getQdrant().getHost())
                .port(properties.getQdrant().getPort())
                .collectionName(properties.getQdrant().getCollectionName())
                .build();
    }

    @Bean
    public QdrantClient qdrantClient(
            KnowledgeAssistantProperties properties) {

        return new QdrantClient(
                QdrantGrpcClient.newBuilder(
                        properties.getQdrant().getHost(),
                        properties.getQdrant().getPort(),
                        false
                ).build()
        );
    }
}
