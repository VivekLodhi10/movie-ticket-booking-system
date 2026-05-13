package com.movie.ticket.booking.system.service.handlers;

import com.movie.ticket.booking.system.service.dtos.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class BookingAPIHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> methodArgumentNotValidException(MethodArgumentNotValidException exception){
        log.info("Entered into BookingAPIHandler class with the exception:" + exception.getMessage());
//        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
//        List<String> errorMessages = new ArrayList<>();
//        for (ObjectError error : errors){
//            errorMessages.add(error.getDefaultMessage())
//        }
//        ResponseDTO responseDTO = ResponseDTO.builder()
//                .errorMessages(errorMessages)
//                .build();
        return new ResponseEntity<ResponseDTO>(ResponseDTO.builder()
                .errorDescription(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .errorMessages(
                        exception.getBindingResult().getAllErrors()
                                .stream()
                                .map(objectError -> objectError.getDefaultMessage())
                                .collect(Collectors.toList())
                ).build()
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDTO> runTimeException(RuntimeException runtimeException){
        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .statusCodeDescription(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .errorDescription(runtimeException.getMessage())
                        .build(),HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
