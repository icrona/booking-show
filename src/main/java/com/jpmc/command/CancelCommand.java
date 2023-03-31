package com.jpmc.command;

import com.jpmc.di.DependencyInjection;
import com.jpmc.service.BookingService;

public class CancelCommand implements Command {

    private final String ticketNo;

    private final String phoneNo;

    private final BookingService bookingService;

    public CancelCommand(String ticketNo, String phoneNo) {
        this.ticketNo = ticketNo;
        this.phoneNo = phoneNo;
        this.bookingService = DependencyInjection.getService(BookingService.class);
    }

    @Override
    public String execute() {
        bookingService.cancelTicket(this);
        return String.format("Ticket %s Cancelled", ticketNo);
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }
}
