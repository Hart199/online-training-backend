package com.future.onlinetraining.controller;

import com.future.onlinetraining.service.ModuleService;
import com.future.onlinetraining.utility.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @GetMapping("/modules")
    public ResponseEntity getAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return new ResponseHelper<>()
                .setSuccessStatus(true)
                .setHttpStatus(HttpStatus.OK)
                .setParam("data", moduleService.getAll(PageRequest.of(page, size)))
                .send();
    }

    @GetMapping("/modules/_top")
    public ResponseEntity getTop(@RequestParam("page") int page, @RequestParam("size") int size) {
        return new ResponseHelper<>()
                .setSuccessStatus(true)
                .setHttpStatus(HttpStatus.OK)
                .setParam("data", moduleService.getAll(PageRequest.of(
                        page, size, Sort.by("rating").descending())))
                .send();
    }

    @GetMapping("/modules/_ratings/{id}")
    public ResponseEntity getRatings(
            @RequestParam("page") int page, @RequestParam("size") int size, @PathVariable("id") int id) {

        return new ResponseHelper<>()
                .setSuccessStatus(true)
                .setHttpStatus(HttpStatus.OK)
                .setParam("data", moduleService.getRatings(
                        id, PageRequest.of(page, size)
                ))
                .send();
    }

    @GetMapping("/modules/_search")
    public ResponseEntity getRatingBySearchTerms(
            @RequestParam("page") int page, @RequestParam("size") int size,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "hasExam", required = false) Boolean hasExam){

        Pageable pageable;

        if (sortBy == null)
            pageable = PageRequest.of(page, size);
        else if (sortBy.equals("rating"))
            pageable = PageRequest.of(page, size, Sort.by("rating").descending());
        else
            pageable = PageRequest.of(page, size, Sort.by("modules.name").descending());

        return new ResponseHelper<>()
                .setSuccessStatus(true)
                .setHttpStatus(HttpStatus.OK)
                .setParam("data", moduleService.getAllBySearchTerm(pageable, name, category, hasExam))
                .send();
    }

}
