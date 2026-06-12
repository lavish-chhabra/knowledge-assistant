package com.augmentaion.rag.service;

import dev.langchain4j.model.chat.ChatModel;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatModel model;

    public ChatService(ChatModel model) {
        this.model = model;
    }

    public String chat(String question) {
        return model.chat(question);
    }
}
