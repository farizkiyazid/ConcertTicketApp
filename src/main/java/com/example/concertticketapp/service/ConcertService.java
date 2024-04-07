package com.example.concertticketapp.service;

import com.example.concertticketapp.model.Concert;
import com.example.concertticketapp.repository.ConcertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService {
    @Autowired
    private ConcertRepository concertRepository;

    public Concert insertConcert(Concert concert) {
        return concertRepository.save(concert);
    }

    public List<Concert> insertConcerts(List<Concert> concerts) {
        return concertRepository.saveAll(concerts);
    }

    public Concert getConcert(Long id) {
        return concertRepository.findById(id).orElse(null);
    }

    public List<Concert> getAllConcerts() {
        return concertRepository.findAll();
    }

    public Concert updateConcert(Concert concert) {
        Concert existingConcert = concertRepository.findById(concert.getId()).orElse(null);
        if (existingConcert == null) {
            return null;
        }
        existingConcert.setName(concert.getName());
        existingConcert.setConcertDateTime(concert.getConcertDateTime());
        existingConcert.setVenue(concert.getVenue());
        existingConcert.setTicketsAvailable(concert.getTicketsAvailable());
        existingConcert.setBookingStartDateTime(concert.getBookingStartDateTime());
        existingConcert.setBookingEndDateTime(concert.getBookingEndDateTime());
        concertRepository.save(existingConcert);
        return existingConcert;
    }

    public boolean deleteConcertById(Long id) {
        Concert existingConcert = concertRepository.findById(id).orElse(null);
        if (existingConcert == null) {
            return false;
        }
        concertRepository.deleteById(id);
        return true;
    }
}

