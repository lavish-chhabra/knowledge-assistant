package com.augmentaion.rag.config;

import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Collections;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class QdrantInitializer {

    private final QdrantClient qdrantClient;
    private final KnowledgeAssistantProperties properties;

    public QdrantInitializer(
            QdrantClient qdrantClient,
            KnowledgeAssistantProperties properties) {

        this.qdrantClient = qdrantClient;
        this.properties = properties;
    }

    @PostConstruct
    public void initializeCollection() {

        try {

            String collectionName =
                    properties.getQdrant()
                            .getCollectionName();

            boolean exists =
                    qdrantClient
                            .collectionExistsAsync(collectionName)
                            .get();

            if (exists) {

                System.out.println(
                        "Qdrant collection already exists: "
                                + collectionName
                );

                return;
            }

            qdrantClient
                    .createCollectionAsync(
                            collectionName,

                            Collections.VectorParams
                                    .newBuilder()
                                    .setSize(
                                            properties
                                                    .getQdrant()
                                                    .getVectorSize()
                                    )
                                    .setDistance(
                                            Collections.Distance.Cosine
                                    )
                                    .build()
                    )
                    .get();

            System.out.println(
                    "Created Qdrant collection: "
                            + collectionName
            );

        } catch (Exception exception) {

            throw new RuntimeException(
                    "Failed to initialize Qdrant collection",
                    exception
            );
        }
    }
}