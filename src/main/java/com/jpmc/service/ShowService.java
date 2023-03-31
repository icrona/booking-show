package com.jpmc.service;

import com.jpmc.command.SetupCommand;
import com.jpmc.command.ViewCommand;
import com.jpmc.dto.ShowDTO;

import java.util.Optional;

public interface ShowService extends Service {

    void setup(SetupCommand command);

    Optional<ShowDTO> view(ViewCommand viewCommand);

}
