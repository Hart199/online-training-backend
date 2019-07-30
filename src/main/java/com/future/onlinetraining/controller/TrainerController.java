package com.future.onlinetraining.controller;

import com.future.onlinetraining.service.TrainerService;
import com.future.onlinetraining.utility.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrainerController {

    @Autowired
    TrainerService trainerService;

    @GetMapping("/trainers/_top")
    public ResponseEntity getTop(@RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "5") int size) {
        return new ResponseHelper<>()
                .setHttpStatus(HttpStatus.OK)
                .setParam("data", trainerService.getTopTrainers(page, size))
                .setSuccessStatus(true)
                .send();
    }
}
