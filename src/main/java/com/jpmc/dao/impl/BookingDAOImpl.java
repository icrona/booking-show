package com.jpmc.dao.impl;

import com.jpmc.dao.BookingDAO;
import com.jpmc.dao.entity.Booking;
import com.jpmc.dao.entity.BookingId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BookingDAOImpl implements BookingDAO {

    private final Map<BookingId, Booking> bookingById;

    private final Map<String, Booking> bookingByTicketNo;

    public BookingDAOImpl() {
        bookingById = new HashMap<>();
        bookingByTicketNo = new HashMap<>();
    }

    @Override
    public Optional<Booking> findByTicketNo(String ticketNo) {
        return Optional.ofNullable(bookingByTicketNo.get(ticketNo));
    }

    @Override
    public Optional<Booking> findByShowNoAndPhoneNo(String showNo, String phoneNo) {
        BookingId bookingId = new BookingId(showNo, phoneNo);

        return Optional.ofNullable(bookingById.get(bookingId));
    }

    @Override
    public Booking createOrUpdate(Booking booking) {
        bookingByTicketNo.put(booking.getTicketNo(), booking);
        bookingById.put(new BookingId(booking.getShowNo(), booking.getPhoneNo()), booking);
        return booking;
    }
}
