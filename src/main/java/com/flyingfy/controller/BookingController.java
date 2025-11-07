package com.flyingfy.controller;

import com.flyingfy.dto.CreateBookingRequest;
import com.flyingfy.model.Booking;
import com.flyingfy.model.User;
import com.flyingfy.service.BookingService;
import com.flyingfy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService service;
    private final UserService userService;
    public BookingController(BookingService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody CreateBookingRequest req,
                                                 @AuthenticationPrincipal UserDetails ud) {
        User user = userService.findByEmail(ud.getUsername());
        Booking created = service.createBooking(req,user);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Booking>> listBookings(@AuthenticationPrincipal UserDetails ud){
        User user = userService.findByEmail(ud.getUsername());
        return ResponseEntity.ok(service.listBookingsForUser(user.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(service.getBooking(id));
    }
}