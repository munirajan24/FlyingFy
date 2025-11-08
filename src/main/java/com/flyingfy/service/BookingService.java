package com.flyingfy.service;

import com.flyingfy.dto.CreateBookingRequest;
import com.flyingfy.exception.ResourceNotFoundException;
import com.flyingfy.model.Booking;
import com.flyingfy.model.User;
import com.flyingfy.repo.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {
    private final BookingRepository repo;

    public BookingService(BookingRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Booking createBooking(CreateBookingRequest req, User user) {
        Booking b = new Booking();
        b.setPassengerName(req.getPassengerName());
        b.setOrigin(req.getOrigin());
        b.setDestination(req.getDestination());
        b.setTravelDate(req.getTravelDate());
        b.setUser(user);  return repo.save(b);
    }

    public List<Booking> listBookings() {
        return repo.findAll();
    }
    public List<Booking> listBookingsForUser(Long userId) {
        return repo.findByUserId(userId);
    }
    public Booking getBooking(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
    }
}