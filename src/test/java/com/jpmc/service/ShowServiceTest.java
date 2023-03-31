package com.jpmc.service;

import com.jpmc.command.SetupCommand;
import com.jpmc.dao.AvailableSeatDAO;
import com.jpmc.dao.BookingDAO;
import com.jpmc.dao.ShowDAO;
import com.jpmc.dao.entity.Seat;
import com.jpmc.dao.entity.Show;
import com.jpmc.dao.impl.ShowDAOImpl;
import com.jpmc.exception.BusinessException;
import com.jpmc.service.impl.ShowServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ShowServiceTest {

    private ShowDAO showDAO;

    private AvailableSeatDAO availableSeatDAO;

    private BookingDAO bookingDAO;

    private ShowService showService;

    @BeforeEach
    public void setup() {
        showDAO = Mockito.mock(ShowDAO.class);
        availableSeatDAO = Mockito.mock(AvailableSeatDAO.class);
        bookingDAO = Mockito.mock(BookingDAO.class);
        showService = new ShowServiceImpl(showDAO, availableSeatDAO, bookingDAO);
    }

    @Test
    void testSetupShowWithZeroRow() {
        SetupCommand setupCommand = new SetupCommand(
                "1",
                0,
                0,
                2
        );

        BusinessException bex = Assertions.assertThrows(BusinessException.class, () -> showService.setup(setupCommand));
        Assertions.assertEquals("Show must have minimum 1 row", bex.getMessage());
    }

    @Test
    void testSetupShowWithExceedingNumberOfRow() {
        SetupCommand setupCommand = new SetupCommand(
                "1",
                30,
                0,
                2
        );

        BusinessException bex = Assertions.assertThrows(BusinessException.class, () -> showService.setup(setupCommand));
        Assertions.assertEquals("Show must have maximum 26 row", bex.getMessage());
    }

    @Test
    void testSetupShowWithZeroSeatsPerRow() {
        SetupCommand setupCommand = new SetupCommand(
                "1",
                1,
                0,
                2
        );

        BusinessException bex = Assertions.assertThrows(BusinessException.class, () -> showService.setup(setupCommand));
        Assertions.assertEquals("Show must have minimum 1 seat per row", bex.getMessage());
    }

    @Test
    void testSetupShowWithExceedingNumberSeatsPerRow() {
        SetupCommand setupCommand = new SetupCommand(
                "1",
                1,
                20,
                2
        );

        BusinessException bex = Assertions.assertThrows(BusinessException.class, () -> showService.setup(setupCommand));
        Assertions.assertEquals("Show must have maximum 10 seat per row", bex.getMessage());
    }

    @Test
    void testSetupShowExists_throwBex() {

        String existingShow = "1";
        SetupCommand setupCommand = new SetupCommand(
                existingShow,
                1,
                1,
                2
        );

        Mockito.when(showDAO.findByShowNo(existingShow))
                .thenReturn(Optional.of(Mockito.mock(Show.class)));

        BusinessException bex = Assertions.assertThrows(BusinessException.class, () -> showService.setup(setupCommand));
        Assertions.assertEquals("Show 1 already exists", bex.getMessage());
    }

    @Test
    void testSetup() {
        String showNo = "1";
        SetupCommand setupCommand = new SetupCommand(
                showNo,
                2,
                2,
                2
        );
        Mockito.when(showDAO.findByShowNo(showNo))
                .thenReturn(Optional.empty());

        showService.setup(setupCommand);

        Set<Seat> seats = Set.of(
                new Seat('A', 1),
                new Seat('A', 2),
                new Seat('B', 1),
                new Seat('B', 2)
        );

        Mockito.verify(showDAO, Mockito.times(1)).create(Mockito.any(Show.class));
        Mockito.verify(availableSeatDAO, Mockito.times(1))
                .addAvailableSeats(showNo, seats);

    }
}
