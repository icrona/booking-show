package com.jpmc.service.impl;

import com.jpmc.dao.AvailableSeatDAO;
import com.jpmc.dao.entity.Seat;
import com.jpmc.dto.AvailabilityDTO;
import com.jpmc.service.BookingService;

import java.util.Set;
import java.util.stream.Collectors;

public class BookingServiceImpl implements BookingService {

    private final AvailableSeatDAO availableSeatDAO;

    public BookingServiceImpl(AvailableSeatDAO availableSeatDAO) {
        this.availableSeatDAO = availableSeatDAO;
    }

    @Override
    public AvailabilityDTO findAvailability(String showNo) {
        Set<Seat> seats = availableSeatDAO.findByShowNo(showNo);
        return new AvailabilityDTO(
                seats.stream()
                        .map(Seat::toString)
                        .collect(Collectors.toList())
        );
    }
}
