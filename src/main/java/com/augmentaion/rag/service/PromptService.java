package com.augmentaion.rag.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PromptService {

    private final String ragPromptTemplate;

    public PromptService() throws IOException {

        ragPromptTemplate =
                Files.readString(
                        Path.of(
                                "src/main/resources/prompts/rag-prompt.txt"
                        )
                );
    }

    public String buildRagPrompt(
            String context,
            String question) {

        return ragPromptTemplate
                .replace("{{context}}", context)
                .replace("{{question}}", question);
    }
}
