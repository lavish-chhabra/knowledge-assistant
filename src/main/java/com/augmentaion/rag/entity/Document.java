package com.augmentaion.rag.entity;

import com.augmentaion.rag.enums.DocumentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "documents")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String fileName;

    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    private LocalDateTime uploadedAt;

    private String uploadedBy;

    private Long chunkCount;

    private Long fileSize;

    private String contentType;

    // getters/setters
}
