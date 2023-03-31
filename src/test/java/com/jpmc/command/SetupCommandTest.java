package com.jpmc.command;

import com.jpmc.di.DependencyInjection;
import com.jpmc.service.ShowService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class SetupCommandTest {

    private SetupCommand setupCommand;

    private ShowService showService;

    @BeforeEach
    public void setup() {
        showService = Mockito.mock(ShowService.class);
        try (MockedStatic<DependencyInjection> di = Mockito.mockStatic(DependencyInjection.class)) {
            di.when(() -> DependencyInjection.getService(ShowService.class))
                    .thenReturn(showService);
            setupCommand = new SetupCommand("showNo", 1, 1, 2);
        }
    }

    @Test
    void testExecute() {
        Assertions.assertEquals("Setup Successful", setupCommand.execute());
        Mockito.verify(showService).setup(setupCommand);
    }
}
