package com.future.onlinetraining.users.controller;

import com.future.onlinetraining.users.service.UserService;
import com.future.onlinetraining.utility.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
                .setHttpStatus(HttpStatus.OK)
                .setMessage("Login Gagal")
                .send();
    }

    @GetMapping("/auth")
    public ResponseEntity unauthenticated(){
        return new ResponseHelper<>()
                .setSuccessStatus(false)
                .setHttpStatus(HttpStatus.OK)
                .setMessage("Anda belum login.")
                .send();
    }

    @GetMapping("/auth/_role")
    public ResponseEntity getRole(){
        if(userService.getUserFromSession() == null){
            return userService.unauthenticated();
        }

        return new ResponseHelper<>()
                .setSuccessStatus(true)
                .setHttpStatus(HttpStatus.OK)
                .setParam("role", userService.getUserFromSession().getRole().getValue())
                .send();
    }
}
