package com.augmentaion.rag.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "knowledge-assistant")
public class KnowledgeAssistantProperties {

    @Valid
    private final Gemini gemini = new Gemini();

    @Valid
    private Qdrant qdrant = new Qdrant();

    @Valid
    private Documents documents = new Documents();

    @Valid
    private Retrieval retrieval = new Retrieval();

    @Getter
    @Setter
    public static class Gemini {

        @NotBlank
        private String apiKey;

        @NotBlank
        private String chatModel = "gemini-2.5-flash";

        @NotBlank
        private String embeddingModel = "text-embedding-004";
    }

    @Getter
    @Setter
    public static class Qdrant {

        @NotBlank
        private String host = "localhost";

        @Min(1)
        @Max(65535)
        private int port = 6334;

        @NotBlank
        private String collectionName = "documents";

        @Positive
        private int vectorSize = 768;
    }

    @Getter
    @Setter
    public static class Documents {

        @Positive
        private int chunkSize = 1000;

        @Min(0)
        private int chunkOverlap = 200;

        @NotNull
        private DataSize maxUploadSize =
                DataSize.ofMegabytes(10);
    }

    @Getter
    @Setter
    public static class Retrieval {

        @Positive
        private int maxResults = 3;
    }
}