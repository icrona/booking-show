package com.jpmc.dao;

import com.jpmc.dao.entity.Seat;

import java.util.Set;

public interface AvailableSeatDAO {

    Set<Seat> findByShowNo(String showNo);

    Set<Seat> addAvailableSeats(String showNo, Set<Seat> seats);

    Set<Seat> removeAvailableSeats(String showNo, Set<Seat> seats);
}
