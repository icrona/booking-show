package com.jpmc.command;

import com.jpmc.di.DependencyInjection;
import com.jpmc.service.BookingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class CancelCommandTest {

    private CancelCommand cancelCommand;

    private BookingService bookingService;

    @BeforeEach
    public void setup() {
        bookingService = Mockito.mock(BookingService.class);
        try (MockedStatic<DependencyInjection> di = Mockito.mockStatic(DependencyInjection.class)) {
            di.when(() -> DependencyInjection.getService(BookingService.class))
                    .thenReturn(bookingService);
            cancelCommand = new CancelCommand("1", "123");
        }
    }

    @Test
    void testExecute() {
        Assertions.assertEquals("Ticket 1 Cancelled", cancelCommand.execute());
        Mockito.verify(bookingService).cancelTicket(cancelCommand);
    }
}
