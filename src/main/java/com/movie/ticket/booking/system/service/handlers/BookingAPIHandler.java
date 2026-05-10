package com.movie.ticket.booking.system.service.handlers;

import com.movie.ticket.booking.system.service.dtos.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class BookingAPIHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> methodArgumentNotValidException(MethodArgumentNotValidException exception){
        log.info("Entered into BookingAPIHandler class with the exception:" + exception.getMessage());
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        ResponseDTO responseDTO = ResponseDTO.builder()
                .errorMessage(errors.get(0).getDefaultMessage())
                .build();
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
    }
}
