package com.jpmc.service.impl;

import com.jpmc.command.BookCommand;
import com.jpmc.command.CancelCommand;
import com.jpmc.command.FindAvailabilityCommand;
import com.jpmc.dao.AvailableSeatDAO;
import com.jpmc.dao.BookingDAO;
import com.jpmc.dao.ShowDAO;
import com.jpmc.dao.entity.Booking;
import com.jpmc.dao.entity.Seat;
import com.jpmc.dao.entity.Show;
import com.jpmc.dto.AvailabilityDTO;
import com.jpmc.exception.BusinessException;
import com.jpmc.service.BookingService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class BookingServiceImpl implements BookingService {

    private final AvailableSeatDAO availableSeatDAO;

    private final ShowDAO showDAO;

    private final BookingDAO bookingDAO;

    public BookingServiceImpl(ShowDAO showDAO, AvailableSeatDAO availableSeatDAO, BookingDAO bookingDAO) {
        this.availableSeatDAO = availableSeatDAO;
        this.showDAO = showDAO;
        this.bookingDAO = bookingDAO;
    }

    @Override
    public AvailabilityDTO findAvailability(FindAvailabilityCommand command) {
        Set<Seat> seats = availableSeatDAO.findByShowNo(command.getShowNo());
        return new AvailabilityDTO(
                seats.stream()
                        .map(Seat::toString)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public UUID bookShow(BookCommand bookCommand) {
        String showNo = bookCommand.getShowNo();
        String phoneNo = bookCommand.getPhoneNo();
        if (showDAO.findByShowNo(showNo).isEmpty()) {
            throw new BusinessException("Show Not Found");
        }

        if(bookingDAO.findByShowNoAndPhoneNo(showNo, phoneNo).isPresent()) {
            throw new BusinessException(String.format("Show Booking with PhoneNo %s already exists", phoneNo));
        }

        Set<Seat> seats = Arrays.stream(bookCommand.getSeats().split(","))
                .map(String::trim)
                .map(seat -> {
                    char row = seat.charAt(0);
                    int seatNo = Integer.parseInt(seat.substring(1));
                    return new Seat(row, seatNo);
                })
                .collect(Collectors.toSet());

        Set<Seat> availableSeat = availableSeatDAO.findByShowNo(showNo);

        for(Seat seat: seats) {
            if(!availableSeat.contains(seat)) {
                throw new BusinessException(String.format("Seat %s not available", seat.toString()));
            }
        }

        availableSeatDAO.removeAvailableSeats(showNo, seats);

        UUID ticketNo = UUID.randomUUID();

        Booking booking = new Booking(
                showNo,
                phoneNo,
                ticketNo.toString(),
                seats
        );
        bookingDAO.createOrUpdate(booking);

        return ticketNo;
    }

    @Override
    public void cancelTicket(CancelCommand command) {
        String ticketNo = command.getTicketNo();
        String phoneNo = command.getPhoneNo();

        Optional<Booking> booking = bookingDAO.findByTicketNo(ticketNo);
        if(booking.isEmpty()) {
            throw new BusinessException("Ticket Not Found");
        }

        Booking bookingToBeCancelled = booking.get();
        if(!bookingToBeCancelled.getPhoneNo().equals(phoneNo)) {
            throw new BusinessException("Unauthorized");
        }

        String showNo = bookingToBeCancelled.getShowNo();
        Show show = showDAO.findByShowNo(showNo).get();

        int cancellationWindowInMinutes = show.getCancellationWindowInMinutes();

        if (bookingToBeCancelled.getDateTime().plusMinutes(cancellationWindowInMinutes).isAfter(LocalDateTime.now())) {
            throw new BusinessException(String.format(
                    "Exceeding Cancellation Window of %d minute(s), Unable to Cancel ticket", cancellationWindowInMinutes
            ));
        }

        availableSeatDAO.addAvailableSeats(showNo, bookingToBeCancelled.getSeats());

        bookingDAO.createOrUpdate(bookingToBeCancelled.cancel());
    }
}
