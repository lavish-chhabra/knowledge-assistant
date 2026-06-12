package com.augmentaion.rag.dto;

public record OllamaRequest (String model,
                             String prompt,
                             boolean stream)
{}
