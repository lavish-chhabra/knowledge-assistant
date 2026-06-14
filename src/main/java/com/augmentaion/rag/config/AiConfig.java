package com.augmentaion.rag.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class AiConfig {

    @Bean
    ChatModel chatLanguageModel(KnowledgeAssistantProperties properties) {
        return OllamaChatModel.builder()
                .baseUrl(properties.getOllama().getBaseUrl())
                .modelName(properties.getOllama().getChatModel())
                .temperature(properties.getOllama().getTemperature())
                .build();
    }

    @Bean
    public EmbeddingModel embeddingModel(KnowledgeAssistantProperties properties) {

        return OllamaEmbeddingModel.builder()
                .baseUrl(properties.getOllama().getBaseUrl())
                .modelName(properties.getOllama().getEmbeddingModel())
                .build();
    }

    @Bean
    RestClient ollamaRestClient(KnowledgeAssistantProperties properties) {

        SimpleClientHttpRequestFactory requestFactory =
                new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(properties.getOllama().getTimeout());
        requestFactory.setReadTimeout(properties.getOllama().getTimeout());

        return RestClient.builder()
                .baseUrl(properties.getOllama().getBaseUrl())
                .requestFactory(requestFactory)
                .build();
    }
}
