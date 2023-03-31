package com.jpmc.command.factory;

import com.jpmc.command.*;
import com.jpmc.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandFactoryTest {

    @Test
    void testCreateCommand_Buyer_SetupShow_notAllowed_throwsBex() {
        BusinessException bex = Assertions.assertThrows(BusinessException.class,
                () -> CommandFactory.createCommand(CommandFactory.BUYER, "Setup showNo 26 10 2"));
        Assertions.assertEquals("Invalid Command", bex.getMessage());
    }

    @Test
    void testCreateCommand_Admin_BookShow_notAllowed_throwsBex() {
        BusinessException bex = Assertions.assertThrows(BusinessException.class,
                () -> CommandFactory.createCommand(CommandFactory.ADMIN, "Book showNo phoneNo A1,A2"));
        Assertions.assertEquals("Invalid Command", bex.getMessage());
    }

    @Test
    void testCreateBookCommand_InvalidSeatFormat_throwsBex() {
        BusinessException bex = Assertions.assertThrows(BusinessException.class,
                () -> CommandFactory.createCommand(CommandFactory.BUYER, "Book showNo phoneNo A1#A2#A3"));
        Assertions.assertEquals("Invalid Seat Argument", bex.getMessage());
    }

    @Test
    void testCreateSetupCommand() {
        Command command = CommandFactory.createCommand(CommandFactory.ADMIN, "Setup showNo 26 10 2");
        Assertions.assertNotNull(command);
        Assertions.assertTrue(command instanceof SetupCommand);
    }

    @Test
    void testCreateViewCommand() {
        Command command = CommandFactory.createCommand(CommandFactory.ADMIN, "View showNo");
        Assertions.assertNotNull(command);
        Assertions.assertTrue(command instanceof ViewCommand);
    }

    @Test
    void testCreateAvailabilityCommand() {
        Command command = CommandFactory.createCommand(CommandFactory.BUYER, "Availability showNo");
        Assertions.assertNotNull(command);
        Assertions.assertTrue(command instanceof FindAvailabilityCommand);
    }
    @Test
    void testCreateBookCommand() {
        Command command = CommandFactory.createCommand(CommandFactory.BUYER, "Book showNo phoneNo A1,A2,A3");
        Assertions.assertNotNull(command);
        Assertions.assertTrue(command instanceof BookCommand);
    }

    @Test
    void testCreateCancelCommand() {
        Command command = CommandFactory.createCommand(CommandFactory.BUYER, "Cancel ticketNo phoneNo");
        Assertions.assertNotNull(command);
        Assertions.assertTrue(command instanceof CancelCommand);
    }
}
