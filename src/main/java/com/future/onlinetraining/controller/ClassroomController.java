package com.future.onlinetraining.controller;

import com.future.onlinetraining.dto.ClassroomDTO;
import com.future.onlinetraining.service.ClassroomService;
import com.future.onlinetraining.utility.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClassroomController {

    @Autowired
    ClassroomService classroomService;

    @GetMapping("/classrooms/_subscribed")
    public ResponseEntity getSubscribed(@RequestParam("page") int page, @RequestParam("size") int size) {
        return new ResponseHelper<>()
                .setHttpStatus(HttpStatus.OK)
                .setParam("data", classroomService.getAllSubscribed(page, size))
                .setSuccessStatus(true)
                .send();
    }

    @GetMapping("/classrooms")
    public ResponseEntity getAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return new ResponseHelper<>()
                .setHttpStatus(HttpStatus.OK)
                .setParam("data", classroomService.all())
                .setSuccessStatus(true)
                .send();
    }

    @PostMapping("/_trainer/classrooms")
    public ResponseEntity create(@RequestBody ClassroomDTO classroomDTO) {
        return new ResponseHelper<>()
                .setParam("data", classroomService.create(classroomDTO))
                .send();
    }
}
