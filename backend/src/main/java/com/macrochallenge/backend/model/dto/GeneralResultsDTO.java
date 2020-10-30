package com.macrochallenge.backend.model.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class GeneralResultsDTO {
    @NonNull
    private String totalPercentageOfCorrectAnswers;

    @NonNull
    private String totalNumberOfQuestions;

    @NonNull
    private String totalNumberOfCorrectAnswers;

    @NonNull
    private List<ResultsPerTopicDTO> resultsPerTopic;
}
