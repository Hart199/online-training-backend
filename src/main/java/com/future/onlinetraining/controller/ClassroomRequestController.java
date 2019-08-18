package com.future.onlinetraining.controller;

import com.future.onlinetraining.dto.ClassroomRequestDTO;
import com.future.onlinetraining.dto.ModuleRequestLikeDTO;
import com.future.onlinetraining.entity.ClassroomRequest;
import com.future.onlinetraining.entity.ModuleRequestLike;
import com.future.onlinetraining.service.ClassroomRequestService;
import com.future.onlinetraining.service.ClassroomService;
import com.future.onlinetraining.utility.ResponseHelper;
import com.future.onlinetraining.utility.ValidationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ClassroomRequestController {

    @Autowired
    ClassroomRequestService classroomRequestService;

    /**
     * get all classroom requests
     * @param page
     * @param size
     * @param popular
     * @param name
     * @return
     */
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

    /**
     * get all user classroom requests
     * @param page
     * @param size
     * @param name
     * @param status
     * @return
     */
    @GetMapping("/classrooms/_requests/_users")
    public ResponseEntity getAllByUser(@RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "size", defaultValue = "5") int size,
                                       @RequestParam(value = "name", required = false) String name,
                                       @RequestParam(value = "status", required = false) String status) {
        return new ResponseHelper<>()
                .setParam("data", classroomRequestService.getAllByUser(
                        PageRequest.of(page, size, JpaSort.unsafe(
                                Sort.Direction.DESC, "max(cr.createdAt)")), name, status))
                .setHttpStatus(HttpStatus.OK)
                .setSuccessStatus(true)
                .send();
    }

    /**
     * Get all trainer classroom requests
     * @param page
     * @param size
     * @param name
     * @param popular
     * @return
     */
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

    /**
     * Add classroom request
     * @param classroomRequestDTO
     * @return
     */
    @PostMapping("/classrooms/_requests")
    public ResponseEntity request(@RequestBody @Valid ClassroomRequestDTO classroomRequestDTO, BindingResult bindingResult) {
        ValidationHandler.validate(bindingResult);
        ClassroomRequest classroomRequest = classroomRequestService.request(classroomRequestDTO);

        if (classroomRequest == null)
            return new ResponseHelper<>()
                    .setMessage("Berhasil membatalkan request kelas")
                    .send();

        return new ResponseHelper<>()
                .setMessage("Berhasil melakukan request kelas")
                .send();
    }

    /**
     * Delete classroom request
     * @param id
     * @return
     */
    @DeleteMapping("/_trainer/classrooms/{id}/_requests/_reject")
    public ResponseEntity reject(@PathVariable("id") int id) {
        classroomRequestService.reject(id);
        return new ResponseHelper<>()
                .setMessage("Berhasil menolak permintaan kelas")
                .send();
    }
}
