package com.macrochallenge.backend.model.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class ResultsPerTopicDTO {
    @NonNull
    private String topic;

    @NonNull
    private String totalPercentageOfCorrectAnswers;

    @NonNull
    private String totalNumberOfCorrectAnswers;

    @NonNull
    private String totalNumberOfAnsweredQuestions;

    @NonNull
    private String totalNumberOfQuestions;
}
