package com.jpmc.dao.entity;

import java.util.Objects;

public class BookingId {

    private final String showNo;

    private final String phoneNo;

    public BookingId(String showNo, String phoneNo) {
        this.showNo = showNo;
        this.phoneNo = phoneNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingId bookingId = (BookingId) o;
        return showNo.equals(bookingId.showNo) && phoneNo.equals(bookingId.phoneNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(showNo, phoneNo);
    }
}
