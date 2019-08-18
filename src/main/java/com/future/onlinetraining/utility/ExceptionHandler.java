package com.future.onlinetraining.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class ExceptionHandler {

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<?> handleException(ValidationException exception) {
        HashMap<String, String[]> errors = new HashMap<>();
        errors.put("message", exception.getMessages());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
