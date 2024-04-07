package com.example.concertticketapp.service;

import com.example.concertticketapp.model.Concert;
import com.example.concertticketapp.model.Ticket;
import com.example.concertticketapp.model.User;
import com.example.concertticketapp.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ConcertService concertService;

    public Ticket bookTicket(long userId, long concertId) {
        // Check if user exists
        User user = userService.getUser(userId);
        if (user == null) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist");
        }

        // Check if concert exists
        Concert concert = concertService.getConcert(concertId);
        if (concert == null) {
            throw new IllegalArgumentException("Concert with ID " + concertId + " does not exist");
        }

        // Check if concert is available
        LocalDateTime currentDate = LocalDateTime.now();
        if (!(currentDate.isBefore(concert.getConcertDateTime()) &&
              currentDate.isAfter(concert.getBookingStartDateTime()) &&
              currentDate.isBefore(concert.getBookingEndDateTime()) &&
              concert.getTicketsAvailable() > 0)) {
            throw new IllegalStateException("Concert is not available for booking");
        }

        // Check if user already has tickets for this concert
        List<Ticket> userTickets = ticketRepository.findByUserIdAndConcertId(userId, concertId);
        if (!userTickets.isEmpty()) {
            throw new IllegalStateException("User already has tickets for this concert");
        }

        // Create ticket object
        Ticket ticket = new Ticket();
        ticket.setUserId(userId);
        ticket.setConcertId(concertId);
        ticket.setPurchaseTime(LocalDateTime.now());
        ticket.setQuantity(1); // Assuming one ticket is booked at a time

        // Insert ticket
        return ticketRepository.save(ticket);
    }


    public List<Ticket> getUserTickets(long userId) {
        // Check if user exists
        User user = userService.getUser(userId);
        if (user == null) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist");
        }

        // Query tickets using userId
        return ticketRepository.findByUserId(userId);
    }
}
