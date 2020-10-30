package com.macrochallenge.backend.model.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class TestDTO {
    @NonNull
    private String testName;

    @NonNull
    private String testYear;
}
