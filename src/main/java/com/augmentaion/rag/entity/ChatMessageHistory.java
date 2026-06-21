package com.augmentaion.rag.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "chat_messages",
        indexes = {
                @Index(
                        name = "idx_chat_session",
                        columnList = "sessionId"
                )
        }
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String sessionId;

    private String role;

    @Column(columnDefinition = "TEXT")
    private String message;

    private LocalDateTime createdAt;
}