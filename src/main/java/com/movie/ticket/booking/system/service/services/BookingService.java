package com.movie.ticket.booking.system.service.services;

import com.movie.ticket.booking.system.service.dtos.BookingDTO;
import com.movie.ticket.booking.system.service.dtos.ResponseDTO;

public interface BookingService {
    public ResponseDTO createBooking(BookingDTO bookingDTO);
}
