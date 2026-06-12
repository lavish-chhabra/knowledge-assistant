package com.augmentaion.rag.controller;

import com.augmentaion.rag.dto.AnswerResponse;
import com.augmentaion.rag.dto.QuestionRequest;
import com.augmentaion.rag.service.RagService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/ask")
    public String ask(
            @RequestParam @NotBlank String question) {

        return ragService.ask(question);
    }

    @PostMapping("/ask")
    public AnswerResponse ask(
            @RequestBody @Valid QuestionRequest request) {

        return new AnswerResponse(ragService.ask(request.question()));
    }
}
