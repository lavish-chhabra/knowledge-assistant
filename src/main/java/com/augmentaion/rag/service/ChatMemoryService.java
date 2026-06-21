package com.augmentaion.rag.service;

import com.augmentaion.rag.entity.ChatMessageHistory;
import com.augmentaion.rag.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMemoryService {

    private final ChatMessageRepository repository;

    public void addMessage(
            String sessionId,
            String role,
            String message) {

        ChatMessageHistory chatMessage =
                ChatMessageHistory.builder()
                        .sessionId(sessionId)
                        .role(role)
                        .message(message)
                        .createdAt(LocalDateTime.now())
                        .build();

        repository.save(chatMessage);
    }

    public String getConversation(
            String sessionId) {

        return repository
                .findBySessionIdOrderByCreatedAtAsc(
                        sessionId
                )
                .stream()
                .map(message ->
                        message.getRole()
                                + ": "
                                + message.getMessage())
                .collect(Collectors.joining("\n"));
    }
}