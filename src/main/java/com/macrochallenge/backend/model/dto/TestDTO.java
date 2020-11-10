package com.macrochallenge.backend.model.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class TestDTO {
    @NonNull
    private String testName;

    @NonNull
    private String testYear;

    @NonNull
    private String numberOfCorrectAnswersForLastResult = "-1";

    @NonNull
    private String totalNumberOfQuestionsForLastResult = "-1";
}
