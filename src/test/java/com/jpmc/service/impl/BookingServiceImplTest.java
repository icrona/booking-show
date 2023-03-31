package com.jpmc.service.impl;

import com.jpmc.command.BookCommand;
import com.jpmc.command.FindAvailabilityCommand;
import com.jpmc.dao.AvailableSeatDAO;
import com.jpmc.dao.BookingDAO;
import com.jpmc.dao.ShowDAO;
import com.jpmc.dao.entity.Booking;
import com.jpmc.dao.entity.Seat;
import com.jpmc.dao.entity.Show;
import com.jpmc.exception.BusinessException;
import com.jpmc.service.BookingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.Set;

class BookingServiceImplTest {

    private ShowDAO showDAO;

    private AvailableSeatDAO availableSeatDAO;

    private BookingDAO bookingDAO;

    private BookingService bookingService;

    @BeforeEach
    public void setup() {
        showDAO = Mockito.mock(ShowDAO.class);
        availableSeatDAO = Mockito.mock(AvailableSeatDAO.class);
        bookingDAO = Mockito.mock(BookingDAO.class);
        bookingService = new BookingServiceImpl(showDAO, availableSeatDAO, bookingDAO);
    }
    @Test
    void testFindAvailability() {
        String showNo = "1";
        FindAvailabilityCommand command = new FindAvailabilityCommand(showNo);

        Mockito.when(availableSeatDAO.findByShowNo(showNo))
                .thenReturn(Set.of(
                        new Seat('A', 10),
                        new Seat('A', 2),
                        new Seat('B', 1)
                ));
        Assertions.assertEquals(List.of("A2", "A10", "B1"), bookingService.findAvailability(command).getSeats());
    }

    @Test
    void testBookShow_notFound_throwBex() {
        String showNo = "1";
        BookCommand command = Mockito.mock(BookCommand.class);
        Mockito.when(command.getShowNo()).thenReturn(showNo);
        Mockito.when(showDAO.findByShowNo(showNo))
                .thenReturn(Optional.empty());
        BusinessException businessException = Assertions.assertThrows(BusinessException.class, () -> bookingService.bookShow(command));
        Assertions.assertEquals("Show Not Found", businessException.getMessage());
    }

    @Test
    void testBookShow_withSamePhoneNo_throwBex() {
        String showNo = "1";
        String phoneNo = "123";
        BookCommand command = Mockito.mock(BookCommand.class);
        Mockito.when(command.getShowNo()).thenReturn(showNo);
        Mockito.when(command.getPhoneNo()).thenReturn(phoneNo);

        Mockito.when(showDAO.findByShowNo(showNo))
                .thenReturn(Optional.of(Mockito.mock(Show.class)));
        Mockito.when(bookingDAO.findByShowNoAndPhoneNo(showNo, phoneNo))
                .thenReturn(Optional.of(Mockito.mock(Booking.class)));

        BusinessException businessException = Assertions.assertThrows(BusinessException.class, () -> bookingService.bookShow(command));
        Assertions.assertEquals("Show Booking with PhoneNo 123 already exists", businessException.getMessage());
    }

    @Test
    void testBookShow_withNonAvailableSeat_throwBex() {
        String showNo = "1";
        String phoneNo = "123";

        BookCommand command = Mockito.mock(BookCommand.class);
        Mockito.when(command.getShowNo()).thenReturn(showNo);
        Mockito.when(command.getPhoneNo()).thenReturn(phoneNo);
        Mockito.when(command.getSeats()).thenReturn("A1");

        Mockito.when(showDAO.findByShowNo(showNo))
                .thenReturn(Optional.of(Mockito.mock(Show.class)));
        Mockito.when(bookingDAO.findByShowNoAndPhoneNo(showNo, phoneNo))
                .thenReturn(Optional.empty());

        Mockito.when(availableSeatDAO.findByShowNo(showNo))
                .thenReturn(Set.of(new Seat('A', 2)));

        BusinessException businessException = Assertions.assertThrows(BusinessException.class, () -> bookingService.bookShow(command));
        Assertions.assertEquals("Seat A1 is not available", businessException.getMessage());
    }

    @Test
    void testBookShow() {
        String showNo = "1";
        String phoneNo = "123";

        BookCommand command = Mockito.mock(BookCommand.class);
        Mockito.when(command.getShowNo()).thenReturn(showNo);
        Mockito.when(command.getPhoneNo()).thenReturn(phoneNo);
        Mockito.when(command.getSeats()).thenReturn("A1");

        Mockito.when(showDAO.findByShowNo(showNo))
                .thenReturn(Optional.of(Mockito.mock(Show.class)));
        Mockito.when(bookingDAO.findByShowNoAndPhoneNo(showNo, phoneNo))
                .thenReturn(Optional.empty());

        Set<Seat> availableSeats = Set.of(new Seat('A', 1));
        Mockito.when(availableSeatDAO.findByShowNo(showNo))
                .thenReturn(availableSeats);

        String ticketNo = bookingService.bookShow(command);

        Mockito.verify(availableSeatDAO, Mockito.times(1)).removeAvailableSeats(showNo, availableSeats);
        Mockito.verify(bookingDAO, Mockito.times(1)).createOrUpdate(Mockito.any(Booking.class));

        Assertions.assertNotNull(ticketNo);
    }

    @Test
    void testCancelTicket() {
        
    }
}
