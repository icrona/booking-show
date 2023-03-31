package com.jpmc.service;

import com.jpmc.command.BookCommand;
import com.jpmc.command.FindAvailabilityCommand;
import com.jpmc.dto.AvailabilityDTO;

import java.util.UUID;

public interface BookingService extends Service {

    AvailabilityDTO findAvailability(FindAvailabilityCommand command);

    UUID bookShow(BookCommand bookCommand);
}
