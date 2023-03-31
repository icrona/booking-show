package com.jpmc.service.impl;

import com.jpmc.command.SetupCommand;
import com.jpmc.dao.AvailableSeatDAO;
import com.jpmc.dao.ShowDAO;
import com.jpmc.dao.entity.Seat;
import com.jpmc.dao.entity.Show;
import com.jpmc.exception.BusinessException;
import com.jpmc.service.ShowService;

import java.util.HashSet;
import java.util.Set;

public class ShowServiceImpl implements ShowService {

    private static final int MIN_ROW = 1;
    private static final int MAX_ROW = 26;

    private static final int MIN_SEATS_PER_ROW = 1;
    private static final int MAX_SEATS_PER_ROW = 10;

    private final ShowDAO showDAO;

    private final AvailableSeatDAO availableSeatDAO;

    public ShowServiceImpl(ShowDAO showDAO, AvailableSeatDAO availableSeatDAO) {
        this.showDAO = showDAO;
        this.availableSeatDAO = availableSeatDAO;
    }

    @Override
    public void setup(SetupCommand command) {

        if(command.getRows() < MIN_ROW) {
            throw new BusinessException(String.format("Show must have minimum %d row", MIN_ROW));
        }

        if(command.getRows() > MAX_ROW) {
            throw new BusinessException(String.format("Show must have maximum %d row", MAX_ROW));
        }

        if(command.getSeatsPerRow() < MIN_SEATS_PER_ROW) {
            throw new BusinessException(String.format("Show must have minimum %d seat per row", MIN_SEATS_PER_ROW));
        }

        if(command.getSeatsPerRow() > MAX_SEATS_PER_ROW) {
            throw new BusinessException(String.format("Show must have maximum %d seat per row", MAX_SEATS_PER_ROW));
        }

        if (showDAO.findByShowNo(command.getShowNo()).isPresent()) {
            throw new BusinessException(String.format("Show %s already exists", command.getShowNo()));
        }

        showDAO.create(new Show(
                command.getShowNo(),
                command.getRows(),
                command.getSeatsPerRow(),
                command.getCancellationWindowInMinutes()
        ));

        availableSeatDAO.addAvailableSeats(command.getShowNo(), generateSeat(command.getRows(), command.getSeatsPerRow()));
    }

    private Set<Seat> generateSeat(int rows, int seatsPerRow) {
        Set<Seat> seats = new HashSet<>();
        for(int row = 0; row < rows; row++) {
            for(int seat = 1; seat <= seatsPerRow; seat++) {
                seats.add(new Seat((char) ('A'+row), seat));
            }
        }
        return seats;
    }
}
