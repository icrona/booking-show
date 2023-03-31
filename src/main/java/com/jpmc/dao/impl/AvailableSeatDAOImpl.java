package com.jpmc.dao.impl;

import com.jpmc.dao.AvailableSeatDAO;
import com.jpmc.dao.entity.Seat;

import java.util.*;

public class AvailableSeatDAOImpl implements AvailableSeatDAO {

    private final Map<String, Set<Seat>> availableSeats;

    public AvailableSeatDAOImpl() {
        availableSeats = new HashMap<>();
    }

    @Override
    public Set<Seat> findByShowNo(String showNo) {
        if (!availableSeats.containsKey(showNo)) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(availableSeats.get(showNo));
    }

    @Override
    public Set<Seat> addAvailableSeats(String showNo, Set<Seat> seats) {
        if(!availableSeats.containsKey(showNo)) {
            availableSeats.put(showNo, new HashSet<>());
        }
        availableSeats.get(showNo).addAll(seats);

        return Collections.unmodifiableSet(availableSeats.get(showNo));
    }

    @Override
    public Set<Seat> removeAvailableSeats(String showNo, Set<Seat> seats) {
        if(!availableSeats.containsKey(showNo)) {
            return Collections.emptySet();
        }

        availableSeats.get(showNo).removeAll(seats);
        return Collections.unmodifiableSet(availableSeats.get(showNo));
    }
}
