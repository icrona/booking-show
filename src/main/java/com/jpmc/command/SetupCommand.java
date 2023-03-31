package com.jpmc.command;

import com.jpmc.di.DependencyInjection;
import com.jpmc.service.ShowService;

public class SetupCommand implements Command {

    private final String showNo;

    private final int rows;

    private final int seatsPerRow;

    private final int cancellationWindowInMinutes;

    private final ShowService showService;

    public SetupCommand(String showNo, int rows, int seatsPerRow, int cancellationWindowInMinutes) {
        this.showNo = showNo;
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;
        this.cancellationWindowInMinutes = cancellationWindowInMinutes;
        this.showService = DependencyInjection.getService(ShowService.class);
    }

    public String getShowNo() {
        return showNo;
    }

    public int getRows() {
        return rows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public int getCancellationWindowInMinutes() {
        return cancellationWindowInMinutes;
    }

    @Override
    public String execute() {
        showService.setup(this);
        return "Setup Successful";
    }
}
