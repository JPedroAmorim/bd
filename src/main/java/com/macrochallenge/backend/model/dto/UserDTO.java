package com.macrochallenge.backend.model.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserDTO {
    @NonNull
    private String userId;
}
