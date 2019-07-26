package com.future.onlinetraining.controller;

import com.future.onlinetraining.dto.ModuleRequestDTO;
import com.future.onlinetraining.dto.ModuleRequestLikeDTO;
import com.future.onlinetraining.entity.ModuleRequest;
import com.future.onlinetraining.entity.ModuleRequestLike;
import com.future.onlinetraining.service.ModuleRequestService;
import com.future.onlinetraining.utility.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ModuleRequestController {

    @Autowired
    ModuleRequestService moduleRequestService;

    @GetMapping("/modules/_requests")
    public ResponseEntity getAllModuleRequests(
            @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam(value = "name", required = false) String name) {
        return new ResponseHelper<>()
                .setParam("data", moduleRequestService.getAll(PageRequest.of(page, size), name))
                .setHttpStatus(HttpStatus.OK)
                .setSuccessStatus(true)
                .send();
    }

    @PostMapping("/modules")
    public ResponseEntity store(@RequestBody @Valid ModuleRequestDTO moduleRequestDTO) {
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
    public ResponseEntity voteLike(@RequestBody @Valid ModuleRequestLikeDTO moduleRequestLikeDTO) {
        ModuleRequestLike moduleRequestLike = moduleRequestService.voteLike(moduleRequestLikeDTO);

        if (moduleRequestLike == null)
            return new ResponseHelper<>()
                .setMessage("Unvote")
                .send();

        return new ResponseHelper<>()
                .setMessage("Voted")
                .send();
    }
}
