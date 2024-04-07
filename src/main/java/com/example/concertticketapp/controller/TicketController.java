package com.example.concertticketapp.controller;


import com.example.concertticketapp.model.APIResponse;
import com.example.concertticketapp.model.BookTicketRequest;
import com.example.concertticketapp.model.ResponseStatus;
import com.example.concertticketapp.model.Ticket;
import com.example.concertticketapp.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/book")
    public APIResponse<Ticket> bookTicket(@RequestBody BookTicketRequest ticketRequest) {
        try {
            Ticket bookedTicket = ticketService.bookTicket(ticketRequest.getUserId(), ticketRequest.getConcertId());
            return new APIResponse<>(ResponseStatus.SUCCESS, null, bookedTicket);
        } catch (IllegalArgumentException e) {
            return new APIResponse<>(ResponseStatus.ERROR, e.getMessage(), null);
        } catch (IllegalStateException e) {
            return new APIResponse<>(ResponseStatus.ERROR, e.getMessage(), null);
        }
    }

    @GetMapping("/user/{userId}")
    public APIResponse<List<Ticket>> getUserTickets(@PathVariable long userId) {
        try {
            List<Ticket> userTickets = ticketService.getUserTickets(userId);
            return new APIResponse<>(ResponseStatus.SUCCESS, null, userTickets);
        } catch (IllegalArgumentException e) {
            return new APIResponse<>(ResponseStatus.ERROR, e.getMessage(), null);
        }
    }
}
