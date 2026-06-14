package com.augmentaion.rag.controller;

import com.augmentaion.rag.dto.AnswerResponse;
import com.augmentaion.rag.dto.QuestionRequest;
import com.augmentaion.rag.service.RagService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/rag")
@Validated
public class RagController {

    private final RagService ragService;

    public RagController(
            RagService ragService) {

        this.ragService = ragService;
    }


    @PostMapping("/ask")
    public AnswerResponse ask(
            @RequestBody @Valid QuestionRequest request) {

        return ragService.ask(request.question());
    }
}
