package com.jpmc.service;

import com.jpmc.command.SetupCommand;

public interface ShowService extends Service {

    void setup(SetupCommand command);

}
