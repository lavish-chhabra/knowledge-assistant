package com.augmentaion.rag.service;

import com.augmentaion.rag.config.KnowledgeAssistantProperties;
import com.augmentaion.rag.dto.OllamaRequest;
import com.augmentaion.rag.dto.OllamaResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class OllamaService {

    private final RestClient restClient;
    private final KnowledgeAssistantProperties properties;

    public OllamaService(
            RestClient ollamaRestClient,
            KnowledgeAssistantProperties properties) {

        this.restClient = ollamaRestClient;
        this.properties = properties;
    }

    public String ask(String question) {

        var request =
                new OllamaRequest(
                        properties.getOllama().getChatModel(),
                        question,
                        false
                );

        var response =
                restClient.post()
                        .uri("/api/generate")
                        .body(request)
                        .retrieve()
                        .body(OllamaResponse.class);

        if (response == null || response.response() == null) {
            throw new IllegalStateException("Ollama returned an empty response");
        }

        return response.response();
    }
}
