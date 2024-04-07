package com.example.concertticketapp.controller;

import com.example.concertticketapp.model.APIResponse;
import com.example.concertticketapp.model.Concert;
import com.example.concertticketapp.model.ResponseStatus;
import com.example.concertticketapp.service.ConcertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concerts")
public class ConcertController {

    @Autowired
    private ConcertService concertService;

    @GetMapping("/")
    public List<Concert> getAllConcerts() {
        return concertService.getAllConcerts();
    }

    @GetMapping("/{id}")
    public APIResponse<Concert> getConcert(@PathVariable Long id) {
        Concert concert = concertService.getConcert(id);
        if (concert == null) {
            return new APIResponse<>(ResponseStatus.ERROR, "Concert with ID " + id + " not found", null);
        } else {
            return new APIResponse<>(ResponseStatus.SUCCESS, null, concert);
        }
    }

    @PostMapping("/add")
    public APIResponse<Concert> addConcert(@RequestBody Concert concert) {
        Concert newConcert = concertService.insertConcert(concert);
        if (newConcert == null) {
            return new APIResponse<>(ResponseStatus.ERROR, "Failed to add concert", null);
        } else {
            return new APIResponse<>(ResponseStatus.SUCCESS, null, newConcert);
        }
    }

    @PutMapping("/update")
    public APIResponse<Concert> updateConcert(@RequestBody Concert concert) {
        Concert updatedConcert = concertService.updateConcert(concert);
        if (updatedConcert == null) {
            return new APIResponse<>(ResponseStatus.ERROR, "Failed to update concert", null);
        } else {
            return new APIResponse<>(ResponseStatus.SUCCESS, null, updatedConcert);
        }
    }

    @DeleteMapping("/remove/{id}")
    public APIResponse removeConcert(@PathVariable Long id) {
        boolean removalStatus = concertService.deleteConcertById(id);
        return removalStatus ? new APIResponse(ResponseStatus.SUCCESS, null, null)
              : new APIResponse(ResponseStatus.ERROR, "Concert with ID " + id + " not found", null);
    }
}

