package com.macrochallenge.backend.model.dto;

import com.macrochallenge.backend.model.Question;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class TestResultDTO {
    @NonNull
    List<Question> questions;
    @NonNull
    String answeredQuestions;
}
