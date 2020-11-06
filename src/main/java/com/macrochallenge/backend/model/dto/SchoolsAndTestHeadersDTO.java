package com.macrochallenge.backend.model.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class SchoolsAndTestHeadersDTO {
    @NonNull
    private String schoolName;

    @NonNull
    private String schoolLocation;

    @NonNull
    private List<TestDTO> tests;
}
