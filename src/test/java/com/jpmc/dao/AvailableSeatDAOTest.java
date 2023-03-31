package com.jpmc.dao;

import com.jpmc.dao.entity.Seat;
import com.jpmc.dao.impl.AvailableSeatDAOImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AvailableSeatDAOTest {

    private AvailableSeatDAO availableSeatDAO;

    @BeforeEach
    public void setup() {
        availableSeatDAO = new AvailableSeatDAOImpl();
    }

    @Test
    void findByShowNo_notFoundShowNo_returnEmptySeats() {
        String notFoundShowNo = "1";

        Assertions.assertEquals(0, availableSeatDAO.findByShowNo(notFoundShowNo).size());
    }
    @Test
    void findByShowNo() {
        String showNo = "1";
        Set<Seat> seats = new HashSet<>();
        seats.add(new Seat('A', 1));
        seats.add(new Seat('A', 2));
        availableSeatDAO.addAvailableSeats(showNo, seats);

        Assertions.assertEquals(2, availableSeatDAO.findByShowNo(showNo).size());
    }

    @Test
    void addAvailableSeatsToNewShow() {

        String showNo = "1";
        Set<Seat> seats = new HashSet<>();
        seats.add(new Seat('A', 1));
        seats.add(new Seat('A', 2));

        Assertions.assertEquals(2, availableSeatDAO.addAvailableSeats(showNo, seats).size());
    }

    @Test
    void addAvailableSeatsToExistingShow() {

        String showNo = "1";
        Set<Seat> seats = new HashSet<>();
        seats.add(new Seat('A', 2));
        seats.add(new Seat('A', 3));

        availableSeatDAO.addAvailableSeats(showNo, Collections.singleton(new Seat('A', 1)));

        Assertions.assertEquals(3, availableSeatDAO.addAvailableSeats(showNo, seats).size());
    }

    @Test
    void removeAvailableSeatsFromNotFoundShow_returnEmpty() {
        String showNo = "1";
        Set<Seat> seatsToRemove = new HashSet<>();
        seatsToRemove.add(new Seat('A', 1));
        seatsToRemove.add(new Seat('A', 2));

        Assertions.assertEquals(0, availableSeatDAO.removeAvailableSeats(showNo, seatsToRemove).size());
    }

    @Test
    void removeAvailableSeats() {
        String showNo = "1";
        Set<Seat> availableSeats = new HashSet<>();
        availableSeats.add(new Seat('A', 1));
        availableSeats.add(new Seat('A', 2));
        availableSeatDAO.addAvailableSeats(showNo, availableSeats);

        Set<Seat> seatsToRemove = new HashSet<>();
        seatsToRemove.add(new Seat('A', 1));

        Assertions.assertEquals(1, availableSeatDAO.removeAvailableSeats(showNo, seatsToRemove).size());
    }
}
