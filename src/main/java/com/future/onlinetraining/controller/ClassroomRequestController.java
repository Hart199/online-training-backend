package com.future.onlinetraining.controller;

import com.future.onlinetraining.service.ClassroomRequestService;
import com.future.onlinetraining.service.ClassroomService;
import com.future.onlinetraining.utility.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClassroomRequestController {

    @Autowired
    ClassroomRequestService classroomRequestService;

    @GetMapping("/classrooms/_requests")
    public ResponseEntity getAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return new ResponseHelper<>()
                .setParam("data", classroomRequestService.getAll(PageRequest.of(page, size)))
                .setHttpStatus(HttpStatus.OK)
                .setSuccessStatus(true)
                .send();
    }
}
