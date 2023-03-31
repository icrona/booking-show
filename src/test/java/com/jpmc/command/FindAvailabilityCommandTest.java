package com.jpmc.command;

import com.jpmc.di.DependencyInjection;
import com.jpmc.dto.AvailabilityDTO;
import com.jpmc.service.BookingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FindAvailabilityCommandTest {

    private FindAvailabilityCommand findAvailabilityCommand;

    private BookingService bookingService;

    @BeforeEach
    public void setup() {
        bookingService = Mockito.mock(BookingService.class);
        try (MockedStatic<DependencyInjection> di = Mockito.mockStatic(DependencyInjection.class)) {
            di.when(() -> DependencyInjection.getService(BookingService.class))
                    .thenReturn(bookingService);
            findAvailabilityCommand = new FindAvailabilityCommand("showNo");
        }
    }
    @Test
    void testExecute() {
        AvailabilityDTO availabilityDTO = new AvailabilityDTO(
                List.of("A1", "A2", "B3")
        );
        Mockito.when(bookingService.findAvailability(findAvailabilityCommand))
                .thenReturn(availabilityDTO);

        Assertions.assertEquals("[A1, A2, B3]", findAvailabilityCommand.execute());
    }
}
