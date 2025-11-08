package com.flyingfy.dto;

import java.time.LocalDate;

public class BookingResponse {
    private Long id;
    private String passengerName;
    private String origin;
    private String destination;
    private LocalDate travelDate;
    private String providerPnr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
    }

    public String getProviderPnr() {
        return providerPnr;
    }

    public void setProviderPnr(String providerPnr) {
        this.providerPnr = providerPnr;
    }

}
