package com.jpmc.di;

import com.jpmc.dao.AvailableSeatDAO;
import com.jpmc.dao.impl.AvailableSeatDAOImpl;
import com.jpmc.dao.impl.BookingDAOImpl;
import com.jpmc.dao.impl.ShowDAOImpl;
import com.jpmc.service.Service;
import com.jpmc.service.ShowService;
import com.jpmc.service.impl.ShowServiceImpl;

import java.util.List;

public class DependencyInjection {

    private static final ShowDAOImpl showDAO = new ShowDAOImpl();
    private static final BookingDAOImpl bookingDAO = new BookingDAOImpl();

    private static final AvailableSeatDAO availableSeatDAO = new AvailableSeatDAOImpl();

    private static final ShowService showService = new ShowServiceImpl(showDAO, availableSeatDAO, bookingDAO);

    private static final List<Service> SERVICES = List.of(showService);

    public static <T extends Service> T getService(Class<T> serviceClass) {
        return SERVICES.stream()
                .filter(serviceClass::isInstance)
                .map(serviceClass::cast)
                .findFirst()
                .get();
    }
}
