package com.augmentaion.rag.controller;

import com.augmentaion.rag.dto.AnswerResponse;
import com.augmentaion.rag.dto.QuestionRequest;
import com.augmentaion.rag.service.RagService;
import com.augmentaion.rag.service.StreamingRagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/rag")
@RequiredArgsConstructor
@Validated
public class RagController {

    private final RagService ragService;
    private final StreamingRagService streamingRagService;

    @PostMapping("/ask")
    public AnswerResponse ask(
            @RequestBody @Valid QuestionRequest request) {

        return ragService.ask(request.sessionId(), request.question());
    }

    @PostMapping(
            value = "/stream",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public SseEmitter stream(
            @RequestBody QuestionRequest request) {

        return streamingRagService.streamAnswer(
                request.question()
        );
    }
}
