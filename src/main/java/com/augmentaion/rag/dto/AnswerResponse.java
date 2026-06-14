package com.augmentaion.rag.dto;

import java.util.List;

public record AnswerResponse(String answer, List<SourceReference> sources) {
}
