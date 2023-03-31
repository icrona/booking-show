package com.jpmc.dao;

import com.jpmc.dao.entity.Booking;

import java.util.Optional;

public interface BookingDAO {

    Optional<Booking> findByTicketNo(String ticketNo);

    Optional<Booking> findByShowNoAndPhoneNo(String showNo, String phoneNo);

    Booking createOrUpdate(Booking booking);

}
