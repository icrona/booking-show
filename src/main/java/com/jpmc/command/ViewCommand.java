package com.jpmc.command;

import com.jpmc.di.DependencyInjection;
import com.jpmc.dto.ShowDTO;
import com.jpmc.service.ShowService;

import java.util.Optional;

public class ViewCommand implements Command {

    private final String showNo;

    private final ShowService showService;

    public ViewCommand(String showNo) {
        this.showNo = showNo;
        this.showService = DependencyInjection.getService(ShowService.class);
    }

    @Override
    public String execute() {
        Optional<ShowDTO> showDTO = showService.view(this);
        if(showDTO.isEmpty()) {
            return "Show Not Found";
        }
        return showDTO.toString();
    }

    public String getShowNo() {
        return showNo;
    }
}
