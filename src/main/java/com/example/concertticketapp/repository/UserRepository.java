package com.example.concertticketapp.repository;

import com.example.concertticketapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
