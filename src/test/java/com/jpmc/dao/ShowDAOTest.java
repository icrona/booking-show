package com.jpmc.dao;

import com.jpmc.dao.entity.Show;
import com.jpmc.dao.impl.ShowDAOImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShowDAOTest {

    private ShowDAO showDAO;

    @BeforeEach
    public void setup() {
        showDAO = new ShowDAOImpl();
    }
    @Test
    void create() {
        String showNo = "1";
        Show show = new Show(
                showNo,
                26,
                10,
                2
        );
        showDAO.create(show);
        Assertions.assertTrue(showDAO.findByShowNo(showNo).isPresent());
    }

    @Test
    void findByShowNo_notFound_returnEmpty() {
        String showNo = "1";
        Assertions.assertTrue(showDAO.findByShowNo(showNo).isEmpty());
    }

    @Test
    void findByShowNo_found_returnShow() {
        String showNo = "1";
        Show show = new Show(
                showNo,
                26,
                10,
                2
        );
        showDAO.create(show);
        Assertions.assertTrue(showDAO.findByShowNo(showNo).isPresent());
    }
}
