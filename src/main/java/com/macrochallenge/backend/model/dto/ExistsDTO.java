package com.macrochallenge.backend.model.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class ExistsDTO {
    @NonNull
    private String exists;
}
