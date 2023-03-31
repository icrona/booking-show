package com.jpmc.command;

import com.jpmc.di.DependencyInjection;
import com.jpmc.dto.ShowDTO;
import com.jpmc.dto.TicketDTO;
import com.jpmc.service.ShowService;
import com.jpmc.service.impl.ShowServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ViewCommandTest {

    private ViewCommand viewCommand;

    private ShowService showService;

    private String showNo = "1";

    @BeforeEach
    public void setup() {
        showService = Mockito.mock(ShowService.class);
        try (MockedStatic<DependencyInjection> di = Mockito.mockStatic(DependencyInjection.class)) {
            di.when(() -> DependencyInjection.getService(ShowService.class))
                    .thenReturn(showService);
            viewCommand = new ViewCommand(showNo);
        }

    }

    @Test
    void testExecute_showNotFound() {
        Mockito.when(showService.view(viewCommand))
                .thenReturn(Optional.empty());

        Assertions.assertEquals("Show Not Found", viewCommand.execute());
    }

    @Test
    void testExecute() {
        TicketDTO ticket  = new TicketDTO("1", "123", List.of("A1", "A2"));
        ShowDTO showDTO = new ShowDTO(
                showNo,
                List.of(ticket)
        );
        Mockito.when(showService.view(viewCommand))
                .thenReturn(Optional.of(showDTO));

        Assertions.assertEquals("Show{showNo='1', tickets=[Ticket{ticketNo='1', phoneNo='123', seats=[A1, A2]}]}", viewCommand.execute());
    }
}
