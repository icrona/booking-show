package com.jpmc.service;

import com.jpmc.dto.AvailabilityDTO;

public interface BookingService extends Service {

    AvailabilityDTO findAvailability(String showNo);
}
