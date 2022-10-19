package ru.practicum.mainserver.common.exceptions;

import lombok.Data;

@Data
public class ErrorResponse {
    String error;
    public ErrorResponse(String error) {
        this.error = error;
    }
}
