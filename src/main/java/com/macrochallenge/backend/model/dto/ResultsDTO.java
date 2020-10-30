package com.macrochallenge.backend.model.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class ResultsDTO {
    @NonNull
    private String testName;

    @NonNull
    private String testYear;

    @NonNull
    private String totalPercentageOfCorrectAnswers;

    @NonNull
    private String totalNumberOfQuestions;

    @NonNull
    private String totalNumberOfAnsweredQuestions;

    @NonNull
    private String totalNumberOfCorrectAnswers;

    @NonNull
    private String correctAnswers;

    @NonNull
    private String wrongAnswers;

    @NonNull
    private String answeredQuestions;

    @NonNull
    private String resultsPerTopic;
}
