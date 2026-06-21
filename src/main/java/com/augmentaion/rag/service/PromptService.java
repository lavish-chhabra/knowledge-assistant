package com.augmentaion.rag.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class PromptService {

    private final String ragPromptTemplate;

    public PromptService() throws IOException {

        ClassPathResource resource =
                new ClassPathResource(
                        "prompts/rag-prompt.txt"
                );

        try (InputStream inputStream =
                     resource.getInputStream()) {

            ragPromptTemplate =
                    new String(
                            inputStream.readAllBytes(),
                            StandardCharsets.UTF_8
                    );
        }
    }

    public String buildRagPrompt(
            String context,
            String conversationHistory,
            String question) {

        return ragPromptTemplate
                .replace("{{conversationHistory}}", conversationHistory)
                .replace("{{context}}", context)
                .replace("{{question}}", question);
    }
}