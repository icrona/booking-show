package com.jpmc.dao.entity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Booking {

    private final String showNo;

    private final String phoneNo;

    private final String ticketNo;

    private final Set<Seat> seats;

    private final LocalDateTime dateTime;

    private final BookingStatus bookingStatus;

    public enum BookingStatus {
        ACTIVE, CANCELLED;
    }

    public Booking(String showNo, String phoneNo, String ticketNo, Set<Seat> seats) {
        this.showNo = showNo;
        this.phoneNo = phoneNo;
        this.ticketNo = ticketNo;
        this.seats = new HashSet<>(seats);
        this.dateTime = LocalDateTime.now();
        this.bookingStatus = BookingStatus.ACTIVE;
    }

    private Booking(String showNo, String phoneNo, String ticketNo, Set<Seat> seats, LocalDateTime dateTime, BookingStatus bookingStatus) {
        this.showNo = showNo;
        this.phoneNo = phoneNo;
        this.ticketNo = ticketNo;
        this.seats = new HashSet<>(seats);
        this.dateTime = dateTime;
        this.bookingStatus = bookingStatus;
    }

    private Booking(Booking booking, BookingStatus bookingStatus) {
        this(booking.showNo,
                booking.phoneNo,
                booking.ticketNo,
                booking.seats,
                booking.dateTime,
                bookingStatus);
    }

    public Booking cancel() {
        return new Booking(this, BookingStatus.CANCELLED);
    }

    public String getShowNo() {
        return showNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public Set<Seat> getSeats() {
        return Collections.unmodifiableSet(seats);
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
