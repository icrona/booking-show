package com.jpmc.mapper;

import com.jpmc.dao.entity.Booking;
import com.jpmc.dao.entity.Show;
import com.jpmc.dto.ShowDTO;
import com.jpmc.dto.TicketDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ShowMapper {

    public static ShowDTO mapShow(Show show, List<Booking> bookings) {
        String showNo = show.getShowNo();
        List<TicketDTO> tickets = bookings.stream()
                .map(TicketMapper::mapTicket)
                .collect(Collectors.toList());
        return new ShowDTO(showNo, tickets);
    }
}
