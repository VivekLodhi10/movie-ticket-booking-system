package com.movie.ticket.booking.system.service.services.impl;

import com.movie.ticket.booking.system.service.brokers.PaymentServiceBroker;
import com.movie.ticket.booking.system.service.dtos.BookingDTO;
import com.movie.ticket.booking.system.service.dtos.ResponseDTO;
import com.movie.ticket.booking.system.service.entities.BookingEntity;
import com.movie.ticket.booking.system.service.enums.BookingStatus;
import com.movie.ticket.booking.system.service.repositories.BookingRepository;
import com.movie.ticket.booking.system.service.services.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentServiceBroker paymentService;

    @Override
    @Transactional
    public ResponseDTO createBooking(BookingDTO bookingDTO) {
        log.info("Entered into bookingServiceImpl createBooking method with request {} " , bookingDTO.toString());
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
                .userId(bookingDTO.getUserId())
                .movieId(bookingDTO.getMovieId())
                .bookingAmount(bookingDTO.getBookingAmount())
                .bookingStatus(BookingStatus.PENDING)
                .showDate(bookingDTO.getShowDate())
                .showTime(bookingDTO.getShowTime())
                .seatsSelected(bookingDTO.getSeatsSelected())
                .build();
        this.bookingRepository.save(bookingEntity);
        bookingDTO.setBookingId(bookingEntity.getBookingId());
//        bookingDTO.setBookingStatus(BookingStatus.PENDING);

        //call payment service
        log.info("Calling service tp do payment for the amount {} with booking id {}",
                bookingEntity.getBookingAmount(),bookingEntity.getBookingId());
        BookingDTO bookingDTOPaymentResponse = paymentService.makePayment(bookingDTO);
        log.info("Payment was successful with booking id {}",bookingEntity.getBookingId());
        bookingEntity.setBookingStatus(bookingDTOPaymentResponse.getBookingStatus());

        return ResponseDTO.builder()
                .bookingDTO(BookingDTO.builder()
                        .bookingId(bookingEntity.getBookingId())
                        .userId(bookingEntity.getUserId())
                        .movieId(bookingEntity.getMovieId())
                        .bookingAmount(bookingEntity.getBookingAmount())
                        .bookingStatus(bookingDTOPaymentResponse.getBookingStatus())
                        .showDate(bookingEntity.getShowDate())
                        .showTime(bookingEntity.getShowTime())
                        .seatsSelected(bookingEntity.getSeatsSelected())
                        .build())
                .build();
    }
}
