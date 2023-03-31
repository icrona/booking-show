package com.jpmc.dto;

import java.util.List;

public class ShowDTO {

    private final String showNo;

    private final List<TicketDTO> tickets;

    public ShowDTO(String showNo, List<TicketDTO> tickets) {
        this.showNo = showNo;
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "Show{" +
                "showNo='" + showNo + '\'' +
                ", tickets=" + tickets +
                '}';
    }
}
