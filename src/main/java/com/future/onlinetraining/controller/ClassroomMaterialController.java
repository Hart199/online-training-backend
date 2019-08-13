package com.future.onlinetraining.controller;

import com.future.onlinetraining.service.ClassroomMaterialService;
import com.future.onlinetraining.utility.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ClassroomMaterialController {

    @Autowired
    ClassroomMaterialService classroomMaterialService;

    @PostMapping("/_trainer/classrooms/{id}/_materials")
    public ResponseEntity add(
            @PathVariable("id") int classroomId, @RequestPart("file") MultipartFile multipartFile) {
        return new ResponseHelper<>()
                .setParam("data", classroomMaterialService.add(classroomId, multipartFile))
                .send();
    }

    @PutMapping("/_trainer/classrooms/{classroomId}/_materials/{materialId}")
    public ResponseEntity update(
            @PathVariable("classroomId") int classroomId,
            @PathVariable("materialId") int materialId,
            @RequestPart("file") MultipartFile multipartFile) {
        return new ResponseHelper<>()
                .setParam("data", classroomMaterialService.update(classroomId, materialId, multipartFile))
                .send();
    }

    @DeleteMapping("/_trainer/classrooms/{classroomId}/_materials/{materialId}")
    public ResponseEntity delete(
            @PathVariable("classroomId") int classroomId,
            @PathVariable("materialId") int materialId) {
        return new ResponseHelper<>()
                .setParam("data", classroomMaterialService.delete(classroomId, materialId))
                .send();
    }
}
