package com.example.concertticketapp.service;

import com.example.concertticketapp.model.Concert;
import com.example.concertticketapp.model.Ticket;
import com.example.concertticketapp.model.User;
import com.example.concertticketapp.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserService userService;

    @Mock
    private ConcertService concertService;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void bookTicket_ValidUserValidConcert_ReturnsBookedTicket() {
        long userId = 1L;
        long concertId = 1L;
        LocalDateTime now = LocalDateTime.now();

        User user = new User();
        user.setId(userId);

        Concert concert = new Concert();
        concert.setId(concertId);
        concert.setConcertDateTime(now.plusDays(1));
        concert.setBookingStartDateTime(now.minusDays(1));
        concert.setBookingEndDateTime(now.plusDays(1));
        concert.setTicketsAvailable(1);

        Ticket ticket = new Ticket();
        ticket.setUserId(userId);
        ticket.setConcertId(concertId);
        ticket.setPurchaseTime(now);
        ticket.setQuantity(1);

        when(userService.getUser(userId)).thenReturn(user);
        when(concertService.getConcert(concertId)).thenReturn(concert);
        when(ticketRepository.findByUserIdAndConcertId(userId, concertId)).thenReturn(Collections.emptyList());
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket bookedTicket = ticketService.bookTicket(userId, concertId);

        assertNotNull(bookedTicket);
        assertEquals(userId, bookedTicket.getUserId());
        assertEquals(concertId, bookedTicket.getConcertId());
        assertEquals(now, bookedTicket.getPurchaseTime());
        assertEquals(1, bookedTicket.getQuantity());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void bookTicket_InvalidUser_ReturnsIllegalArgumentException() {
        long userId = 1L;
        long concertId = 1L;

        when(userService.getUser(userId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> ticketService.bookTicket(userId, concertId));
        verify(concertService, never()).getConcert(concertId);
        verify(ticketRepository, never()).save(any(Ticket.class));
    }

    @Test
    void bookTicket_InvalidConcert_ReturnsIllegalArgumentException() {
        long userId = 1L;
        long concertId = 1L;

        User user = new User();
        user.setId(userId);

        when(userService.getUser(userId)).thenReturn(user);
        when(concertService.getConcert(concertId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> ticketService.bookTicket(userId, concertId));
        verify(ticketRepository, never()).save(any(Ticket.class));
    }

    @Test
    void bookTicket_ConcertNotAvailableForBooking_ReturnsIllegalStateException() {
        long userId = 1L;
        long concertId = 1L;
        LocalDateTime now = LocalDateTime.now();

        User user = new User();
        user.setId(userId);

        Concert concert = new Concert();
        concert.setId(concertId);
        concert.setConcertDateTime(now.minusDays(1));
        concert.setBookingStartDateTime(now.minusDays(2));
        concert.setBookingEndDateTime(now.minusDays(1));
        concert.setTicketsAvailable(1);

        when(userService.getUser(userId)).thenReturn(user);
        when(concertService.getConcert(concertId)).thenReturn(concert);

        assertThrows(IllegalStateException.class, () -> ticketService.bookTicket(userId, concertId));
        verify(ticketRepository, never()).save(any(Ticket.class));
    }

    @Test
    void bookTicket_UserAlreadyHasTicketsForConcert_ReturnsIllegalStateException() {
        long userId = 1L;
        long concertId = 1L;
        LocalDateTime now = LocalDateTime.now();

        User user = new User();
        user.setId(userId);

        Concert concert = new Concert();
        concert.setId(concertId);
        concert.setConcertDateTime(now.plusDays(1));
        concert.setBookingStartDateTime(now.minusDays(1));
        concert.setBookingEndDateTime(now.plusDays(1));
        concert.setTicketsAvailable(1);

        Ticket existingTicket = new Ticket();
        existingTicket.setUserId(userId);
        existingTicket.setConcertId(concertId);

        when(userService.getUser(userId)).thenReturn(user);
        when(concertService.getConcert(concertId)).thenReturn(concert);
        when(ticketRepository.findByUserIdAndConcertId(userId, concertId)).thenReturn(Collections.singletonList(existingTicket));

        assertThrows(IllegalStateException.class, () -> ticketService.bookTicket(userId, concertId));
        verify(ticketRepository, never()).save(any(Ticket.class));
    }

    @Test
    void getUserTickets_ValidUserId_ReturnsListOfTickets() {
        long userId = 1L;
        Ticket ticket1 = new Ticket();
        ticket1.setId(1L);
        ticket1.setUserId(userId);
        Ticket ticket2 = new Ticket();
        ticket2.setId(2L);
        ticket2.setUserId(userId);
        List<Ticket> expectedTickets = Arrays.asList(ticket1, ticket2);

        when(userService.getUser(userId)).thenReturn(new User());
        when(ticketRepository.findByUserId(userId)).thenReturn(expectedTickets);

        List<Ticket> actualTickets = ticketService.getUserTickets(userId);

        assertEquals(expectedTickets.size(), actualTickets.size());
        assertTrue(actualTickets.containsAll(expectedTickets));
    }

    @Test
    void getUserTickets_InvalidUserId_ReturnsIllegalArgumentException() {
        long userId = 1L;

        when(userService.getUser(userId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> ticketService.getUserTickets(userId));
        verify(ticketRepository, never()).findByUserId(userId);
    }
}
