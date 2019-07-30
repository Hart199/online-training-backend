package com.future.onlinetraining.users.controller;

import com.future.onlinetraining.users.service.UserService;
import com.future.onlinetraining.utility.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity all(@RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "5") int size) {
        return new ResponseHelper<>()
                .setHttpStatus(HttpStatus.OK)
                .setParam("data", userService.findAll(PageRequest.of(page, size)))
                .setSuccessStatus(true)
                .send();
    }

}
