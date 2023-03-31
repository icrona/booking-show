package com.jpmc.dao.entity;

import java.util.Objects;

public class Seat {

    private final char row;

    private final int seatNo;

    public Seat(char row, int seatNo) {
        this.row = row;
        this.seatNo = seatNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return row == seat.row && seatNo == seat.seatNo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, seatNo);
    }

    @Override
    public String toString() {
        return row + "" + seatNo;
    }

    public char getRow() {
        return row;
    }

    public int getSeatNo() {
        return seatNo;
    }
}
