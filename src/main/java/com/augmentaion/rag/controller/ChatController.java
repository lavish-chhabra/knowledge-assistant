package com.augmentaion.rag.controller;

import com.augmentaion.rag.dto.AnswerResponse;
import com.augmentaion.rag.dto.QuestionRequest;
import com.augmentaion.rag.service.ChatService;
import com.augmentaion.rag.service.OllamaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

@RestController
@RequiredArgsConstructor
@Validated
public class ChatController {

    private final OllamaService ollamaService;
    private final ChatService chatService;

    @GetMapping("/langchain/chat")
    public String getLangchainChat(@RequestParam @NotBlank String question) {
        return chatService.chat(question);
    }

    @GetMapping("/chat")
    public String getChat(@RequestParam @NotBlank String question) {
        return ollamaService.ask(question);
    }

}
