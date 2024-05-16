package com.wide.agus.exception;

import com.wide.agus.dto.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class Handler extends ResponseEntityExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = ProductOrderException.class)
    public ResponseEntity<?> resp(ProductOrderException e) {
        log.info(e.getMessage());
        return new ResponseEntity<>(new CommonResponse<>(e.getCode(), e.getMessage()), HttpStatus.OK);
    }
}
