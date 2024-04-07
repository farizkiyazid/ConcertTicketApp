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
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "concert_id")
    private Long concertId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "purchase_time")
    private LocalDateTime purchaseTime;
    private int quantity;
}

