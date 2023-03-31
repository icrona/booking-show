package com.jpmc.dao;

import com.jpmc.dao.entity.Show;

import java.util.Optional;

public interface ShowDAO {

    Show create(Show show);

    Optional<Show> findByShowNo(String showNo);
}
