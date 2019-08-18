package com.future.onlinetraining.controller;

import com.future.onlinetraining.service.UserService;
import com.future.onlinetraining.utility.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    UserService userService;

    /**
     * auth when success
     * @return
     */
    @GetMapping("/auth/success")
    public ResponseEntity authSuccessController(){
        return new ResponseHelper<>()
                .setSuccessStatus(true)
                .setHttpStatus(HttpStatus.OK)
                .setMessage("Login Sukses")
                .send();
    }

    /**
     * auth when failed
     * @return
     */
    @GetMapping("/auth/failed")
    public ResponseEntity authFailedController(){
        return new ResponseHelper<>()
                .setSuccessStatus(false)
                .setHttpStatus(HttpStatus.OK)
                .setMessage("Login Gagal")
                .send();
    }

    /**
     * redirect if unauthenticated
     * @return
     */
    @GetMapping("/auth")
    public ResponseEntity unauthenticated(){
        return new ResponseHelper<>()
                .setSuccessStatus(false)
                .setHttpStatus(HttpStatus.OK)
                .setMessage("Anda belum login.")
                .send();
    }

    /**
     * get current role
     * @return
     */
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
