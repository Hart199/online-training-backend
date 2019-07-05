package com.future.onlinetraining.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ResponseHelper<T> {

    private boolean success = false;
    private HttpStatus httpStatus =  HttpStatus.NOT_FOUND;
    private String message = null;
    private HashMap results = new HashMap();

    public ResponseHelper setSuccessStatus(boolean status){
        this.success = status;
        return this;
    }

    public ResponseHelper setHttpStatus(HttpStatus httpStatus){
        this.httpStatus = httpStatus;
        return this;
    }

    public ResponseHelper setMessage(String message){
        this.message = message;
        return this;
    }

    public ResponseHelper setParam(String key, T value){
        this.results.put(key, value);
        return this;
    }

    public ResponseEntity send(){
        this.results.put("status", this.success);

        if(this.message != null)
            this.results.put("message", message);

        return new ResponseEntity(this.results, this.httpStatus);
    }
}
