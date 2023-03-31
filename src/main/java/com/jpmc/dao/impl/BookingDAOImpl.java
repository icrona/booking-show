package com.jpmc.dao.impl;

import com.jpmc.dao.BookingDAO;
import com.jpmc.dao.entity.Booking;

import java.util.*;
import java.util.stream.Collectors;

public class BookingDAOImpl implements BookingDAO {

    private List<Booking> bookings;

    public BookingDAOImpl() {
        bookings = new ArrayList<>();
    }

    @Override
    public Optional<Booking> findByTicketNo(String ticketNo) {
        return bookings.stream()
                .filter(booking -> booking.getTicketNo().equals(ticketNo))
                .filter(booking -> booking.getBookingStatus().equals(Booking.BookingStatus.ACTIVE))
                .findFirst();
    }

    @Override
    public Optional<Booking> findByShowNoAndPhoneNo(String showNo, String phoneNo) {
        return bookings.stream()
                .filter(booking -> booking.getShowNo().equals(showNo) && booking.getPhoneNo().equals(phoneNo))
                .filter(booking -> booking.getBookingStatus().equals(Booking.BookingStatus.ACTIVE))
                .findFirst();
    }

    @Override
    public List<Booking> findByShowNo(String showNo) {
        return bookings.stream()
                .filter(booking -> booking.getShowNo().equals(showNo))
                .filter(booking -> booking.getBookingStatus().equals(Booking.BookingStatus.ACTIVE))
                .collect(Collectors.toList());
    }

    @Override
    public Booking createOrUpdate(Booking booking) {
        Optional<Booking> existingBooking = bookings.stream()
                .filter(b -> b.getTicketNo().equals(booking.getTicketNo()))
                .findFirst();

        bookings.removeIf(b -> existingBooking.isPresent() && existingBooking.get().getTicketNo().equals(b.getTicketNo()));

        bookings.add(booking);
        return booking;
    }
}
