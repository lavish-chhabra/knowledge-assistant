package com.augmentaion.rag.service;

import com.augmentaion.rag.dto.RetrievedChunk;
import dev.langchain4j.model.chat.ChatModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RagService {

    private final RetrievalService retrievalService;
    private final ChatModel chatModel;
    private final PromptService promptService;

    public String ask(String question) {

        List<RetrievedChunk> chunks =
                retrievalService.search(question);

        String context = chunks.stream()
                .map(RetrievedChunk::content)
                .collect(Collectors.joining("\n\n"));

        String prompt =
                promptService.buildRagPrompt(
                        context,
                        question
                );

        return chatModel.chat(prompt);
    }
}
