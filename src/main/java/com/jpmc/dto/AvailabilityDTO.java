package com.jpmc.dto;

import java.util.List;

public class AvailabilityDTO {

    private final List<String> seats;

    public AvailabilityDTO(List<String> seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return seats.toString();
    }
}
