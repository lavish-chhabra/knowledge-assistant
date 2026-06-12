package com.augmentaion.rag.service;

import dev.langchain4j.model.chat.ChatModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RagService {

    private final RetrievalService retrievalService;
    private final ChatModel chatModel;

    public RagService(
            RetrievalService retrievalService,
            ChatModel chatModel) {

        this.retrievalService = retrievalService;
        this.chatModel = chatModel;
    }

    public String ask(String question) {

        // Step 1: Retrieve relevant chunks
        List<String> chunks =
                retrievalService.search(question);

        // Step 2: Merge chunks into context
        String context =
                String.join("\n\n", chunks);

        // Step 3: Create prompt
        String prompt =
                """
                You are a helpful assistant.

                Answer ONLY using the context below.

                Context:
                %s

                Question:
                %s
                """
                        .formatted(context, question);

        // Step 4: Generate answer
        return chatModel.chat(prompt);
    }
}
