package com.future.onlinetraining.users.controller;

import com.future.onlinetraining.users.service.UserService;
import com.future.onlinetraining.utility.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    UserService userService;

    @GetMapping("/auth/success")
    public ResponseEntity authSuccessController(){
        return new ResponseHelper<>()
                .setSuccessStatus(true)
                .setHttpStatus(HttpStatus.OK)
                .setMessage("Login Sukses")
                .send();
    }

    @GetMapping("/auth/failed")
    public ResponseEntity authFailedController(){
        return new ResponseHelper<>()
                .setSuccessStatus(false)
                .setHttpStatus(HttpStatus.UNAUTHORIZED)
                .setMessage("Login Gagal")
                .send();
    }

}
