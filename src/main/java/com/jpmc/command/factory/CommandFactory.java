package com.jpmc.command.factory;

import com.jpmc.command.*;
import com.jpmc.exception.BusinessException;

import java.util.Arrays;

public class CommandFactory {

    public static final int ADMIN = 1;
    public static final int BUYER = 2;

    public static Command createCommand(int user, String command) {
        if (command.startsWith("Setup") && user == ADMIN) {
            command = command.replaceAll("Setup ", "");
            if(command.matches("\\w+\\s\\d+\\s\\d+\\s\\d+")) {
                String[] args = command.split("\\s");
                return new SetupCommand(args[0],
                        Integer.parseInt(args[1]),
                        Integer.parseInt(args[2]),
                        Integer.parseInt(args[3]));
            }
        }
        if (command.startsWith("View") && user == ADMIN) {
            command = command.replaceAll("View ", "");
            if(command.matches("\\w+")) {
                return new ViewCommand(command);
            }
        }
        if (command.startsWith("Availability") && user == BUYER) {
            command = command.replaceAll("Availability ", "");
            if(command.matches("\\w+")) {
                return new FindAvailabilityCommand(command);
            }
        }
        if (command.startsWith("Book") && user == BUYER) {
            command = command.replaceAll("Book ", "");
            if(command.matches("\\w+\\s\\w+\\s.*")) {
                String[] args = command.split("\\s");
                String seatArgs = args[2];
                validateSeatsArgs(seatArgs);
                return new BookCommand(args[0], args[1], seatArgs);
            }
        }
        if (command.startsWith("Cancel") && user == BUYER) {
            command = command.replaceAll("Cancel ", "");
            if(command.matches("\\w+\\s\\w+")) {
                String[] args = command.split("\\s");
                return new CancelCommand(args[0], args[1]);
            }
        }
        throw new BusinessException("Invalid Command");
    }

    private static void validateSeatsArgs(String seats) {
        Arrays.stream(seats.split(","))
                .map(String::trim)
                .forEach(seat -> {
                    if(seat.isBlank() || !seat.matches("[A-Z]\\d+")) {
                        throw new BusinessException("Invalid Seat Argument");
                    }
                });
    }
}
