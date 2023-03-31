package com.jpmc.dao.impl;

import com.jpmc.dao.ShowDAO;
import com.jpmc.dao.entity.Show;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ShowDAOImpl implements ShowDAO {

    private final Map<String, Show> shows;

    public ShowDAOImpl() {
        shows = new HashMap<>();
    }

    @Override
    public Show create(Show show) {
        shows.put(show.getShowNo(), show);
        return shows.get(show.getShowNo());
    }

    @Override
    public Optional<Show> findByShowNo(String showNo) {
        return Optional.ofNullable(shows.get(showNo));
    }
}
