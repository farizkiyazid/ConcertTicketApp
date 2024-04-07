package com.example.concertticketapp.repository;

import com.example.concertticketapp.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUserId(long userId);

    List<Ticket> findByUserIdAndConcertId(long userId, long concertId);
}
