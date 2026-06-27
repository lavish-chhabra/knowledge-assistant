package com.augmentaion.rag.service;

import com.augmentaion.rag.dto.RetrievedChunk;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StreamingRagService {

    private final StreamingChatModel streamingChatModel;
    private final RetrievalService retrievalService;
    private final PromptService promptService;
    private final ChatMemoryService chatMemoryService;

    public SseEmitter streamAnswer(
            String sessionId,
            String question) {

        SseEmitter emitter =
                new SseEmitter(300000L);

        try {

            String conversationHistory =
                    chatMemoryService.getConversation(
                            sessionId
                    );

            chatMemoryService.addMessage(
                    sessionId,
                    "User",
                    question
            );

            List<RetrievedChunk> chunks =
                    retrievalService.search(
                            question
                    );

            String context =
                    chunks.stream()
                            .map(
                                    RetrievedChunk::content
                            )
                            .collect(
                                    Collectors.joining(
                                            "\n\n"
                                    )
                            );

            String prompt =
                    promptService.buildRagPrompt(
                            context,
                            conversationHistory,
                            question
                    );

            StringBuilder fullResponse =
                    new StringBuilder();

            streamingChatModel.chat(
                    prompt,

                    new StreamingChatResponseHandler() {

                        @Override
                        public void onPartialResponse(
                                String token) {

                            try {

                                fullResponse.append(
                                        token
                                );

                                emitter.send(
                                        token
                                );

                            } catch (
                                    Exception exception) {

                                exception.printStackTrace();

                                emitter.completeWithError(
                                        exception
                                );
                            }
                        }

                        @Override
                        public void onCompleteResponse(
                                ChatResponse response) {

                            chatMemoryService
                                    .addMessage(
                                            sessionId,
                                            "Assistant",
                                            fullResponse
                                                    .toString()
                                    );

                            emitter.complete();
                        }

                        @Override
                        public void onError(
                                Throwable error) {

                            error.printStackTrace();

                            emitter.completeWithError(
                                    error
                            );
                        }
                    }
            );

        } catch (Exception exception) {

            exception.printStackTrace();

            emitter.completeWithError(
                    exception
            );
        }

        return emitter;
    }
}