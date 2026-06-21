package com.augmentaion.rag.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiEmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiStreamingChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    @Bean
    public ChatModel chatLanguageModel(
            KnowledgeAssistantProperties properties) {

        return GoogleAiGeminiChatModel.builder()
                .apiKey(properties.getGemini().getApiKey())
                .modelName(properties.getGemini().getChatModel())
                .build();
    }

    @Bean
    public EmbeddingModel embeddingModel(
            KnowledgeAssistantProperties properties) {

        return GoogleAiEmbeddingModel.builder()
                .apiKey(properties.getGemini().getApiKey())
                .modelName(properties.getGemini().getEmbeddingModel())
                .build();
    }

    @Bean
    StreamingChatModel streamingChatModel(
            KnowledgeAssistantProperties properties) {

        return GoogleAiGeminiStreamingChatModel.builder()
                .apiKey(properties.getGemini().getApiKey())
                .modelName(properties.getGemini().getChatModel())
                .build();
    }
}
