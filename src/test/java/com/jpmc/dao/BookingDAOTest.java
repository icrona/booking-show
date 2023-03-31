package com.jpmc.dao;

import com.jpmc.dao.entity.Booking;
import com.jpmc.dao.entity.Seat;
import com.jpmc.dao.impl.BookingDAOImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class BookingDAOTest {

    private BookingDAO bookingDAO;

    @BeforeEach
    public void setup() {
        bookingDAO = new BookingDAOImpl();
    }
    @Test
    void findByTicketNo_NotFound_returnsEmpty() {
        String notFoundTicketNo = "1";
        Assertions.assertTrue(bookingDAO.findByTicketNo(notFoundTicketNo).isEmpty());
    }

    @Test
    void findByTicketNo() {
        String ticketNo = "1";
        Booking booking = new Booking(
                "showNo",
                "phoneNo",
                ticketNo,
                Collections.singleton(new Seat('A', 1))
        );
        bookingDAO.createOrUpdate(booking);
        Assertions.assertTrue(bookingDAO.findByTicketNo(ticketNo).isPresent());
    }

    @Test
    void findByShowNo() {
        String showNo = "1";
        Booking booking = new Booking(
                showNo,
                "phoneNo",
                "ticketNo",
                Collections.singleton(new Seat('A', 1))
        );
        bookingDAO.createOrUpdate(booking);
        Assertions.assertEquals(1, bookingDAO.findByShowNo(showNo).size());
    }

    @Test
    void findByShowNoAndPhoneNo_NotFound_returnsEmpty() {

        String showNo = "1";
        String phoneNo = "1";
        Assertions.assertTrue(bookingDAO.findByShowNoAndPhoneNo(showNo, phoneNo).isEmpty());
    }

    @Test
    void findByShowNoAndPhoneNo() {
        String showNo = "1";
        String phoneNo = "1";
        Booking booking = new Booking(
                showNo,
                phoneNo,
                "ticketNo",
                Collections.singleton(new Seat('A', 1))
        );
        bookingDAO.createOrUpdate(booking);
        Assertions.assertTrue(bookingDAO.findByShowNoAndPhoneNo(showNo, phoneNo).isPresent());
    }

    @Test
    void createOrUpdate_createNewBooking() {

        Booking booking = new Booking(
                "showNo",
                "phoneNo",
                "ticketNo",
                Collections.singleton(new Seat('A', 1))
        );

        Booking createdBooking = bookingDAO.createOrUpdate(booking);
        Assertions.assertNotNull(createdBooking);
        Assertions.assertEquals(Booking.BookingStatus.ACTIVE, createdBooking.getBookingStatus());
    }

    @Test
    void createOrUpdate_cancelBooking() {

        String showNo = "showNo";
        Booking booking = new Booking(
                showNo,
                "phoneNo",
                "ticketNo",
                Collections.singleton(new Seat('A', 1))
        );

        Booking exisitingBooking = bookingDAO.createOrUpdate(booking);
        Assertions.assertEquals(1, bookingDAO.findByShowNo(showNo).size());
        Booking cancelledBooking = bookingDAO.createOrUpdate(booking.cancel(exisitingBooking));
        Assertions.assertEquals(0, bookingDAO.findByShowNo(showNo).size());

        Assertions.assertNotNull(cancelledBooking);
        Assertions.assertEquals(Booking.BookingStatus.ACTIVE, exisitingBooking.getBookingStatus());
        Assertions.assertEquals(Booking.BookingStatus.CANCELLED, cancelledBooking.getBookingStatus());
    }
}
