package com.example.concertticketapp.repository;

import com.example.concertticketapp.model.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertRepository extends JpaRepository<Concert, Long> {}
