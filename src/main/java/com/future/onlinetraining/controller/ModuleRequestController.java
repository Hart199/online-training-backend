package com.future.onlinetraining.controller;

import com.future.onlinetraining.dto.ModuleRequestDTO;
import com.future.onlinetraining.dto.ModuleRequestLikeDTO;
import com.future.onlinetraining.entity.ModuleRequest;
import com.future.onlinetraining.entity.ModuleRequestLike;
import com.future.onlinetraining.service.ModuleRequestService;
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
public class ModuleRequestController {

    @Autowired
    ModuleRequestService moduleRequestService;

    @GetMapping("/modules/_requests")
    public ResponseEntity getAllModuleRequests(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "popular", defaultValue = "false") Boolean popular) {

        Pageable pageable;
        if (popular)
            pageable = PageRequest.of(page, size, JpaSort.unsafe(
                    Sort.Direction.DESC, "case when count(mrl) is null then 0.0 else count(mrl) end"));
        else
            pageable = PageRequest.of(page, size, Sort.by("id").descending());

        return new ResponseHelper<>()
                .setParam("data", moduleRequestService.getAll(pageable, name))
                .setHttpStatus(HttpStatus.OK)
                .setSuccessStatus(true)
                .send();
    }

    @GetMapping("/modules/_requests/_users")
    public ResponseEntity getAllModuleRequestsByUser(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "popular", defaultValue = "false") Boolean popular) {

        Pageable pageable;
        if (popular)
            pageable = PageRequest.of(page, size, JpaSort.unsafe(
                    Sort.Direction.DESC, "case when count(mrl) is null then 0.0 else count(mrl) end"));
        else
            pageable = PageRequest.of(page, size, Sort.by("id").descending());

        return new ResponseHelper<>()
                .setParam("data", moduleRequestService.getAllByUser(pageable, name, status))
                .setHttpStatus(HttpStatus.OK)
                .setSuccessStatus(true)
                .send();
    }

    @PostMapping("/modules/_requests")
    public ResponseEntity store(
            @RequestBody @Valid ModuleRequestDTO moduleRequestDTO, BindingResult bindingResult) {
        ValidationHandler.validate(bindingResult);
        ResponseHelper responseHelper = new ResponseHelper();
        ModuleRequest moduleRequest = moduleRequestService.store(moduleRequestDTO);

        if (moduleRequest == null)
            return responseHelper
                    .setSuccessStatus(false)
                    .setMessage("Failed.")
                    .send();

        return  responseHelper
                    .setSuccessStatus(true)
                    .setParam("data", moduleRequest)
                    .setMessage("Success")
                    .send();
    }

    @PostMapping("/modules/_likes")
    public ResponseEntity voteLike(
            @RequestBody @Valid ModuleRequestLikeDTO moduleRequestLikeDTO, BindingResult bindingResult) {
        ValidationHandler.validate(bindingResult);
        ModuleRequestLike moduleRequestLike = moduleRequestService.voteLike(moduleRequestLikeDTO);

        if (moduleRequestLike == null)
            return new ResponseHelper<>()
                .setMessage("Unvote")
                .send();

        return new ResponseHelper<>()
                .setMessage("Voted")
                .send();
    }

    @PutMapping("/_trainer/modules/_requests/{id}/_status/{status}")
    public ResponseEntity editStatus(
            @PathVariable("id") int id, @PathVariable("status") String status) {
        ModuleRequest moduleRequest = moduleRequestService.changeStatus(id, status);
        if (moduleRequest == null)
            return new ResponseHelper<>()
                    .setSuccessStatus(false)
                    .setMessage("Data tidak ditemukan")
                    .send();

        return new ResponseHelper<>()
                .setParam("data", moduleRequest)
                .send();
    }

}
