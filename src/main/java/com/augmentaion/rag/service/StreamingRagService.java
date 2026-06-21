package com.augmentaion.rag.service;

import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class StreamingRagService {

    private final StreamingChatModel streamingChatModel;

    public SseEmitter streamAnswer(
            String prompt) {

        SseEmitter emitter =
                new SseEmitter(300000L);

        streamingChatModel.chat(
                prompt,

                new StreamingChatResponseHandler() {

                    @Override
                    public void onPartialResponse(
                            String token) {

                        try {

                            emitter.send(token);

                        } catch (Exception exception) {

                            emitter.completeWithError(
                                    exception
                            );
                        }
                    }

                    @Override
                    public void onCompleteResponse(
                            ChatResponse response) {

                        emitter.complete();
                    }

                    @Override
                    public void onError(
                            Throwable error) {

                        emitter.completeWithError(
                                error
                        );
                    }
                }
        );

        return emitter;
    }
}