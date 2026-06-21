package com.augmentaion.rag.repository;

import com.augmentaion.rag.entity.ChatMessageHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChatMessageRepository
        extends JpaRepository<ChatMessageHistory, UUID> {

    List<ChatMessageHistory> findBySessionIdOrderByCreatedAtAsc(
            String sessionId
    );
}