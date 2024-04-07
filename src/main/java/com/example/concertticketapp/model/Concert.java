package com.example.concertticketapp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "concerts")
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "concert_datetime")
    private LocalDateTime concertDateTime;
    private String venue;
    @Column(name = "tickets_available")
    private int ticketsAvailable;
    @Column(name = "booking_start_datetime")
    private LocalDateTime bookingStartDateTime;
    @Column(name = "booking_end_datetime")
    private LocalDateTime bookingEndDateTime;
}
