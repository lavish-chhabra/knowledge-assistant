package com.augmentaion.rag.controller;

import jakarta.validation.constraints.NotBlank;
import com.augmentaion.rag.service.RetrievalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/rag")
@Validated
public class QueryController {

    private final RetrievalService retrievalService;

    public QueryController(
            RetrievalService retrievalService) {

        this.retrievalService = retrievalService;
    }

    @GetMapping("/search")
    public List<String> search(
            @RequestParam @NotBlank String question) {

        return retrievalService.search(question);
    }
}
