package com.macrochallenge.backend.exceptions;

import lombok.Getter;

@Getter
public class AlreadyExistsException extends RuntimeException {

    private final String message;

    public AlreadyExistsException(String message) {
        this.message = message;
    }
}

