package com.future.onlinetraining.controller;

import com.future.onlinetraining.service.ModuleRequestService;
import com.future.onlinetraining.utility.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
