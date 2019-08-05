package com.future.onlinetraining.service;

import com.future.onlinetraining.dto.ClassroomDTO;
import com.future.onlinetraining.dto.ClassroomDetailDTO;
import com.future.onlinetraining.dto.ModuleClassroomDTO;
import com.future.onlinetraining.entity.Classroom;
import com.future.onlinetraining.entity.Module;
import com.future.onlinetraining.entity.projection.ClassroomData;
import com.future.onlinetraining.entity.projection.ClassroomDetailData;
import com.future.onlinetraining.entity.projection.ClassroomSubscribed;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ClassroomService {

    Page<Classroom> getAllPageableClassroom();
    Page<Classroom> getAll(Pageable pageable);
    Page<ClassroomSubscribed> getAllSubscribed(int page, int size);
    Page<ClassroomData> all(String name, Boolean hasExam, Pageable pageable);

    Classroom create(ClassroomDTO classroomDTO);
    Classroom createModuleAndClassroom(ModuleClassroomDTO moduleClassroomDTO);
    ClassroomDetailData getClassroomDetail(Integer id);
    Classroom editDetail(Integer id, ClassroomDetailDTO classroomDTO, MultipartFile[] multipartFiles);
    Boolean delete(Integer id);
    Boolean deleteMaterial(Integer id);
}
