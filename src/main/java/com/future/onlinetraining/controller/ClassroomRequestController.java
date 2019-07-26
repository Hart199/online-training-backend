package com.future.onlinetraining.controller;

import com.future.onlinetraining.dto.ClassroomRequestDTO;
import com.future.onlinetraining.dto.ModuleRequestLikeDTO;
import com.future.onlinetraining.entity.ClassroomRequest;
import com.future.onlinetraining.entity.ModuleRequestLike;
import com.future.onlinetraining.service.ClassroomRequestService;
import com.future.onlinetraining.service.ClassroomService;
import com.future.onlinetraining.utility.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PostMapping("/classrooms/_requests")
    public ResponseEntity request(@RequestBody @Valid ClassroomRequestDTO classroomRequestDTO) {
        ClassroomRequest classroomRequest = classroomRequestService.request(classroomRequestDTO);

        if (classroomRequest == null)
            return new ResponseHelper<>()
                    .setMessage("Berhasil melakukan request kelas")
                    .send();

        return new ResponseHelper<>()
                .setMessage("Berhasil membatalkan request kelas")
                .send();
    }
}
