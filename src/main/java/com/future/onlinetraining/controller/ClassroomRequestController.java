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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ClassroomRequestController {

    @Autowired
    ClassroomRequestService classroomRequestService;

    @GetMapping("/classrooms/_requests")
    public ResponseEntity getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "5") int size,
                                 @RequestParam(value = "popular", defaultValue = "false") Boolean popular,
                                 @RequestParam(value = "name", required = false) String name) {
        Pageable pageable;
        if (popular)
            pageable = PageRequest.of(page, size, Sort.by("requesterCount").descending());
        else
            pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        return new ResponseHelper<>()
                .setParam("data", classroomRequestService.getAll(pageable, name))
                .setHttpStatus(HttpStatus.OK)
                .setSuccessStatus(true)
                .send();
    }

    @GetMapping("/classrooms/_requests/_users")
    public ResponseEntity getAllByUser(@RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "size", defaultValue = "5") int size,
                                       @RequestParam(value = "name", required = false) String name,
                                       @RequestParam(value = "status", required = false) String status) {
        return new ResponseHelper<>()
                .setParam("data", classroomRequestService.getAllByUser(
                        PageRequest.of(page, size, Sort.by("createdAt").descending()), name, status))
                .setHttpStatus(HttpStatus.OK)
                .setSuccessStatus(true)
                .send();
    }

    @GetMapping("/_trainer/classrooms/_requests")
    public ResponseEntity getAllByTrainer(@RequestParam(value = "page", defaultValue = "0") int page,
                                          @RequestParam(value = "size", defaultValue = "5") int size,
                                          @RequestParam(value = "name", required = false) String name,
                                          @RequestParam(value = "popular", defaultValue = "false") boolean popular) {
        Pageable pageable;
        if (popular)
            pageable = PageRequest.of(page, size, Sort.by("requesterCount").descending());
        else
            pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        return new ResponseHelper<>()
                .setParam("data", classroomRequestService.getAllByTrainer(pageable, name))
                .setHttpStatus(HttpStatus.OK)
                .setSuccessStatus(true)
                .send();
    }

    @PostMapping("/classrooms/_requests")
    public ResponseEntity request(@RequestBody @Valid ClassroomRequestDTO classroomRequestDTO) {
        ClassroomRequest classroomRequest = classroomRequestService.request(classroomRequestDTO);

        if (classroomRequest == null)
            return new ResponseHelper<>()
                    .setMessage("Berhasil membatalkan request kelas")
                    .send();

        return new ResponseHelper<>()
                .setMessage("Berhasil melakukan request kelas")
                .send();
    }

    @PutMapping("/_trainer/classrooms/_requests/{id}/_status/{status}")
    public ResponseEntity editStatus(@PathVariable("id") int id, @PathVariable("status") String status) {
        ClassroomRequest classroomRequest = classroomRequestService.editStatus(id, status);
        if (classroomRequest == null)
            return new ResponseHelper<>()
                    .setSuccessStatus(false)
                    .setMessage("Gagal merubah status permintaan kelas")
                    .send();

        return new ResponseHelper<>()
                .setParam("data", classroomRequest)
                .setMessage("Berhasil merubah status permintaan kelas")
                .send();
    }
}
