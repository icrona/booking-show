package com.jpmc.command;

import com.jpmc.di.DependencyInjection;
import com.jpmc.service.BookingService;

public class FindAvailabilityCommand implements Command {

    private final String showNo;

    private final BookingService bookingService;

    public FindAvailabilityCommand(String showNo) {
        this.showNo = showNo;
        this.bookingService = DependencyInjection.getService(BookingService.class);
    }

    @Override
    public String execute() {
        return bookingService.findAvailability(showNo).toString();
    }
}
