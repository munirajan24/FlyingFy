package com.flyingfy.controller;

import com.flyingfy.dto.BookingResponse;
import com.flyingfy.dto.CreateBookingRequest;
import com.flyingfy.dto.UserResponse;
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
    public ResponseEntity<BookingResponse> createBooking(
            @Valid @RequestBody CreateBookingRequest req,
            @AuthenticationPrincipal UserDetails ud) {
        User user = userService.findByEmail(ud.getUsername());
        Booking created = service.createBooking(req, user);
        return ResponseEntity.ok(toDto(created));
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> listBookings(@AuthenticationPrincipal UserDetails ud){
        User user = userService.findByEmail(ud.getUsername());
        List<Booking> bookings = service.listBookingsForUser(user.getId());
        List<BookingResponse> dtoList = bookings.stream()
                .map(this::toDto)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable Long id){
        return ResponseEntity.ok(toDto(service.getBooking(id)));
    }

    private BookingResponse toDto(Booking b) {
        BookingResponse br = new BookingResponse();
        br.setId(b.getId());
        br.setPassengerName(b.getPassengerName());
        br.setOrigin(b.getOrigin());
        br.setDestination(b.getDestination());
        br.setTravelDate(b.getTravelDate());
        br.setProviderPnr(b.getProviderPnr());
        return br;
    }
}