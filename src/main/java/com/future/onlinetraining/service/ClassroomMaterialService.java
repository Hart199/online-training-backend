package com.future.onlinetraining.service;

import com.future.onlinetraining.entity.ClassroomMaterial;
import org.springframework.web.multipart.MultipartFile;

public interface ClassroomMaterialService {

    ClassroomMaterial add(int classroomId, MultipartFile multipartFile);
    ClassroomMaterial update(int classroomId, int materialId, MultipartFile multipartFile);
    boolean delete(int classroomId, int materialId);

}
