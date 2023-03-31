package com.jpmc.dto;

import java.util.List;

public class TicketDTO {

    private final String ticketNo;

    private final String phoneNo;

    private final List<String> seats;

    public TicketDTO(String ticketNo, String phoneNo, List<String> seats) {
        this.ticketNo = ticketNo;
        this.phoneNo = phoneNo;
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketNo='" + ticketNo + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", seats=" + seats +
                '}';
    }
}
