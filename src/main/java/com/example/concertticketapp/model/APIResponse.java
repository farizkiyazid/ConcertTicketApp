package com.example.concertticketapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class APIResponse<T> {
    private ResponseStatus  status;
    private String errorMessage;
    private T data;
}
