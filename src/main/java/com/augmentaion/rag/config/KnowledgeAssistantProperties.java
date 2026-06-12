package com.augmentaion.rag.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "knowledge-assistant")
public class KnowledgeAssistantProperties {

    @Valid
    private final Ollama ollama = new Ollama();

    @Valid
    private final Qdrant qdrant = new Qdrant();

    @Valid
    private final Documents documents = new Documents();

    @Valid
    private final Retrieval retrieval = new Retrieval();

    public Ollama getOllama() {
        return ollama;
    }

    public Qdrant getQdrant() {
        return qdrant;
    }

    public Documents getDocuments() {
        return documents;
    }

    public Retrieval getRetrieval() {
        return retrieval;
    }

    public static class Ollama {

        @NotBlank
        private String baseUrl = "http://localhost:11434";

        @NotBlank
        private String chatModel = "qwen3";

        @NotBlank
        private String embeddingModel = "nomic-embed-text";

        private Duration timeout = Duration.ofSeconds(60);

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String getChatModel() {
            return chatModel;
        }

        public void setChatModel(String chatModel) {
            this.chatModel = chatModel;
        }

        public String getEmbeddingModel() {
            return embeddingModel;
        }

        public void setEmbeddingModel(String embeddingModel) {
            this.embeddingModel = embeddingModel;
        }

        public Duration getTimeout() {
            return timeout;
        }

        public void setTimeout(Duration timeout) {
            this.timeout = timeout;
        }
    }

    public static class Qdrant {

        @NotBlank
        private String host = "localhost";

        @Min(1)
        @Max(65535)
        private int port = 6334;

        @NotBlank
        private String collectionName = "documents";

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getCollectionName() {
            return collectionName;
        }

        public void setCollectionName(String collectionName) {
            this.collectionName = collectionName;
        }
    }

    public static class Documents {

        @Positive
        private int chunkSize = 1000;

        @Min(0)
        private int chunkOverlap = 200;

        private DataSize maxUploadSize = DataSize.ofMegabytes(10);

        public int getChunkSize() {
            return chunkSize;
        }

        public void setChunkSize(int chunkSize) {
            this.chunkSize = chunkSize;
        }

        public int getChunkOverlap() {
            return chunkOverlap;
        }

        public void setChunkOverlap(int chunkOverlap) {
            this.chunkOverlap = chunkOverlap;
        }

        public DataSize getMaxUploadSize() {
            return maxUploadSize;
        }

        public void setMaxUploadSize(DataSize maxUploadSize) {
            this.maxUploadSize = maxUploadSize;
        }
    }

    public static class Retrieval {

        @Positive
        private int maxResults = 3;

        public int getMaxResults() {
            return maxResults;
        }

        public void setMaxResults(int maxResults) {
            this.maxResults = maxResults;
        }
    }
}
