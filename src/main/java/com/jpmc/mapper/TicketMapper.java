package com.jpmc.mapper;

import com.jpmc.dao.entity.Booking;
import com.jpmc.dao.entity.Seat;
import com.jpmc.dto.TicketDTO;

import java.util.stream.Collectors;

public class TicketMapper {

    public static TicketDTO mapTicket(Booking booking) {
        return new TicketDTO(
                booking.getTicketNo(),
                booking.getPhoneNo(),
                booking.getSeats()
                        .stream()
                        .map(Seat::toString)
                        .collect(Collectors.toList())
        );
    }
}
