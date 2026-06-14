package com.augmentaion.rag.service;

import com.augmentaion.rag.dto.AnswerResponse;
import com.augmentaion.rag.dto.RetrievedChunk;
import com.augmentaion.rag.dto.SourceReference;
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

    public AnswerResponse ask(String question) {

        List<RetrievedChunk> chunks =
                retrievalService.search(question);

        List<SourceReference> sources =
                chunks.stream()
                        .map(chunk ->
                                new SourceReference(
                                        chunk.fileName(),
                                        chunk.chunkNumber()
                                ))
                        .distinct()
                        .toList();

        String context = chunks.stream()
                .map(RetrievedChunk::content)
                .collect(Collectors.joining("\n\n"));

        String prompt =
                promptService.buildRagPrompt(
                        context,
                        question
                );

        String chatResponse = chatModel.chat(prompt);
        return new AnswerResponse(chatResponse, sources);
    }
}
