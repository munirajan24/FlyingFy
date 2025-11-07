package com.flyingfy.controller;

import com.flyingfy.dto.CreateBookingRequest;
import com.flyingfy.model.Booking;
import com.flyingfy.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody CreateBookingRequest req) {
        Booking created = service.createBooking(req);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Booking>> listBookings() {
        return ResponseEntity.ok(service.listBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(service.getBooking(id));
    }
}