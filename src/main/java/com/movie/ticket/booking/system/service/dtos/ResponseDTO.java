package com.movie.ticket.booking.system.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private String errorMessage;
    private BookingDTO bookingDTO;
}
