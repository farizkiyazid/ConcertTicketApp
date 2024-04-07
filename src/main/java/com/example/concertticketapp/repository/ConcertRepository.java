package com.example.concertticketapp.repository;

import com.example.concertticketapp.model.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ConcertRepository extends JpaRepository<Concert, Long> {
    @Query("SELECT c FROM Concert c " +
          "WHERE c.concertDateTime > :currentDate " +
          "AND c.ticketsAvailable > 0 " +
          "AND :currentDate >= c.bookingStartDateTime " +
          "AND :currentDate <= c.bookingEndDateTime")
    List<Concert> findAvailableConcerts(@Param("currentDate") LocalDateTime currentDate);
}
