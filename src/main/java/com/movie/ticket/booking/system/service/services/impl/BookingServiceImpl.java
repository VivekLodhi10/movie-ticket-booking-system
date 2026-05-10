package com.movie.ticket.booking.system.service.services.impl;

import com.movie.ticket.booking.system.service.dtos.BookingDTO;
import com.movie.ticket.booking.system.service.entities.BookingEntity;
import com.movie.ticket.booking.system.service.enums.BookingStatus;
import com.movie.ticket.booking.system.service.repositories.BookingRepository;
import com.movie.ticket.booking.system.service.services.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public void createBooking(BookingDTO bookingDTO) {
        log.info("Entered into bookingServiceImpl createBooking method with request data: " + bookingDTO);
//        BookingEntity bookingEntity = new BookingEntity();
//        bookingEntity.setMovieId(bookingEntity.getMovieId());
//        bookingEntity.setBookingAmount(bookingDTO.getBookingAmount());
//        bookingEntity.setBookingStatus(BookingStatus.PENDING);
//        bookingEntity.setShowDate(bookingDTO.getShowDate());
//        bookingEntity.setShowTime(bookingDTO.getShowTime());
//        bookingEntity.setSeatsSelected(bookingDTO.getSeatsSelected());
//        this.bookingRepository.save(bookingEntity);

        // with builder
        BookingEntity bookingEntity = BookingEntity.builder()
                .movieId(bookingDTO.getMovieId())
                .bookingAmount(bookingDTO.getBookingAmount())
                .bookingStatus(BookingStatus.PENDING)
                .showDate(bookingDTO.getShowDate())
                .showTime(bookingDTO.getShowTime())
                .seatsSelected(bookingDTO.getSeatsSelected())
                .build();
        this.bookingRepository.save(bookingEntity);
    }
}
