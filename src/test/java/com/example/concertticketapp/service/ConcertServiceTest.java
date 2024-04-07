package com.example.concertticketapp.service;

import com.example.concertticketapp.model.Concert;
import com.example.concertticketapp.repository.ConcertRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {

    @Mock
    private ConcertRepository concertRepository;

    @InjectMocks
    private ConcertService concertService;

    @Test
    void insertConcert_ValidConcert_ReturnsSavedConcert() {
        Concert concert = new Concert();
        concert.setName("Test Concert");
        when(concertRepository.save(any(Concert.class))).thenReturn(concert);

        Concert savedConcert = concertService.insertConcert(concert);
        assertNotNull(savedConcert);
        assertEquals("Test Concert", savedConcert.getName());
        verify(concertRepository, times(1)).save(any(Concert.class));
    }

    @Test
    void getConcert_ExistingConcertId_ReturnsConcert() {
        long concertId = 1L;
        Concert concert = new Concert();
        concert.setId(concertId);
        concert.setName("Test Concert");

        when(concertRepository.findById(concertId)).thenReturn(Optional.of(concert));

        Concert retrievedConcert = concertService.getConcert(concertId);
        assertNotNull(retrievedConcert);
        assertEquals("Test Concert", retrievedConcert.getName());
    }

    @Test
    void getConcert_NonExistingConcertId_ReturnsNull() {
        long nonExistingConcertId = 999L;

        when(concertRepository.findById(nonExistingConcertId)).thenReturn(Optional.empty());

        Concert retrievedConcert = concertService.getConcert(nonExistingConcertId);
        assertNull(retrievedConcert);
    }

    @Test
    void getAllConcerts_ReturnsListOfConcerts() {
        Concert concert1 = new Concert();
        concert1.setId(1L);
        concert1.setName("Concert 1");

        Concert concert2 = new Concert();
        concert2.setId(2L);
        concert2.setName("Concert 2");

        when(concertRepository.findAll()).thenReturn(Arrays.asList(concert1, concert2));

        List<Concert> concertList = concertService.getAllConcerts();
        assertNotNull(concertList);
        assertEquals(2, concertList.size());
        assertEquals("Concert 1", concertList.get(0).getName());
        assertEquals("Concert 2", concertList.get(1).getName());
    }

    @Test
    void updateConcert_ExistingConcert_ReturnsUpdatedConcert() {
        Concert existingConcert = new Concert();
        existingConcert.setId(1L);
        existingConcert.setName("Existing Concert");

        Concert updatedConcert = new Concert();
        updatedConcert.setId(1L);
        updatedConcert.setName("Updated Concert");

        when(concertRepository.findById(existingConcert.getId())).thenReturn(Optional.of(existingConcert));
        when(concertRepository.save(any(Concert.class))).thenReturn(updatedConcert);

        Concert result = concertService.updateConcert(updatedConcert);
        assertNotNull(result);
        assertEquals("Updated Concert", result.getName());
    }

    @Test
    void updateConcert_NonExistingConcert_ReturnsNull() {
        Concert nonExistingConcert = new Concert();
        nonExistingConcert.setId(999L);
        nonExistingConcert.setName("Non-Existing Concert");

        when(concertRepository.findById(nonExistingConcert.getId())).thenReturn(Optional.empty());

        Concert result = concertService.updateConcert(nonExistingConcert);
        assertNull(result);
    }

    @Test
    void deleteConcertById_ExistingConcertId_ReturnsTrue() {
        long existingConcertId = 1L;
        Concert existingConcert = new Concert();
        existingConcert.setId(existingConcertId);
        existingConcert.setName("Existing Concert");

        when(concertRepository.findById(existingConcertId)).thenReturn(Optional.of(existingConcert));

        boolean result = concertService.deleteConcertById(existingConcertId);
        assertTrue(result);
        verify(concertRepository, times(1)).deleteById(existingConcertId);
    }

    @Test
    void deleteConcertById_NonExistingConcertId_ReturnsFalse() {
        long nonExistingConcertId = 999L;

        when(concertRepository.findById(nonExistingConcertId)).thenReturn(Optional.empty());

        boolean result = concertService.deleteConcertById(nonExistingConcertId);

        assertFalse(result);
        verify(concertRepository, never()).deleteById(nonExistingConcertId);
    }

    @Test
    void getAvailableConcerts_ReturnsListOfConcerts() {
        LocalDateTime currentDate = LocalDateTime.now();
        Concert concert1 = new Concert();
        concert1.setId(1L);
        concert1.setName("Concert 1");

        Concert concert2 = new Concert();
        concert2.setId(2L);
        concert2.setName("Concert 2");

        when(concertRepository.findAvailableConcerts(currentDate)).thenReturn(Arrays.asList(concert1, concert2));

        List<Concert> availableConcerts = concertService.getAvailableConcerts();

        assertNotNull(availableConcerts);
        assertEquals(2, availableConcerts.size());
        assertEquals("Concert 1", availableConcerts.get(0).getName());
        assertEquals("Concert 2", availableConcerts.get(1).getName());
    }
}