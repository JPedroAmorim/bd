package com.macrochallenge.backend.model.dto;

import lombok.*;

@Getter
public class ExistsDTO {
    @NonNull
    private final String exists;

    public ExistsDTO(@NonNull String exists) {
        this.exists = exists;
    }
}
