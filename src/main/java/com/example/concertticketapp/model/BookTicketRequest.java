package com.example.concertticketapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookTicketRequest {
    private long userId;
    private long concertId;
}
