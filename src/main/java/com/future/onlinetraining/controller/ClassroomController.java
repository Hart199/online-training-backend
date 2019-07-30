package com.future.onlinetraining.controller;

import com.future.onlinetraining.dto.ClassroomDTO;
import com.future.onlinetraining.service.ClassroomService;
import com.future.onlinetraining.utility.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClassroomController {

    @Autowired
    ClassroomService classroomService;

    @GetMapping("/classrooms/_subscribed")
    public ResponseEntity getSubscribed(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "5") int size) {
        return new ResponseHelper<>()
                .setHttpStatus(HttpStatus.OK)
                .setParam("data", classroomService.getAllSubscribed(page, size))
                .setSuccessStatus(true)
                .send();
    }

    @GetMapping("/classrooms")
    public ResponseEntity getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "popular", defaultValue = "false") boolean popular,
            @RequestParam(value = "hasExam", required = false) Boolean hasExam) {

        Pageable pageable;

        if (!popular)
            pageable = PageRequest.of(page, size);
        else
            pageable = PageRequest.of(page, size, Sort.by("rating").descending());

        return new ResponseHelper<>()
                .setHttpStatus(HttpStatus.OK)
                .setParam("data", classroomService.all(name, hasExam, pageable))
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
