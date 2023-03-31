package com.jpmc.command;

import com.jpmc.di.DependencyInjection;
import com.jpmc.service.BookingService;
import com.jpmc.service.ShowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class BookCommandTest {

    private BookCommand bookCommand;

    private BookingService bookingService;

    @BeforeEach
    public void setup() {
        bookingService = Mockito.mock(BookingService.class);
        try (MockedStatic<DependencyInjection> di = Mockito.mockStatic(DependencyInjection.class)) {
            di.when(() -> DependencyInjection.getService(BookingService.class))
                    .thenReturn(bookingService);
            bookCommand = new BookCommand("showNo", "1", "A1,A2");
        }
    }
    @Test
    void testExecute() {
        bookCommand.execute();
        Mockito.verify(bookingService).bookShow(bookCommand);
    }
}
