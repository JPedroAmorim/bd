package com.macrochallenge.backend.model.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NonNull
    private String userId;
}
