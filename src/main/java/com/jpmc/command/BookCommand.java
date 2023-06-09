package com.jpmc.command;

import com.jpmc.di.DependencyInjection;
import com.jpmc.service.BookingService;

public class BookCommand implements Command {

    private final String showNo;

    private final String phoneNo;

    private final String seats;

    private final BookingService bookingService;

    public BookCommand(String showNo, String phoneNo, String seats) {
        this.showNo = showNo;
        this.phoneNo = phoneNo;
        this.seats = seats;
        this.bookingService = DependencyInjection.getService(BookingService.class);
    }

    @Override
    public String execute() {
        return bookingService.bookShow(this);
    }

    public String getShowNo() {
        return showNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getSeats() {
        return seats;
    }
}
