package com.jpmc.dao.entity;

public class Show {

    private final String showNo;

    private final int rows;

    private final int seatsPerRow;

    private final int cancellationWindowInMinutes;

    public Show(String showNo, int rows, int seatsPerRow, int cancellationWindowInMinutes) {
        this.showNo = showNo;
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;
        this.cancellationWindowInMinutes = cancellationWindowInMinutes;
    }

    public String getShowNo() {
        return showNo;
    }
}
