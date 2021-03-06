package com.future.onlinetraining.service;

import com.future.onlinetraining.dto.ClassroomDTO;
import com.future.onlinetraining.dto.ClassroomDetailDTO;
import com.future.onlinetraining.dto.ModuleClassroomDTO;
import com.future.onlinetraining.dto.SetScoreDTO;
import com.future.onlinetraining.entity.Classroom;
import com.future.onlinetraining.entity.ClassroomResult;
import com.future.onlinetraining.entity.projection.ClassroomData;
import com.future.onlinetraining.entity.projection.ClassroomDetailData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClassroomService<T> {

    Page<Classroom> getAllPageableClassroom();
    Page<Classroom> getAll(Pageable pageable);
    Page<Classroom> getAllSubscribed(int page, int size);
    Page<ClassroomData> all(String name, Boolean hasExam, Pageable pageable);
    Page<Classroom> getTrainerClassrooms(Pageable pageable, String status);
    Page<ClassroomResult> getClassroomHistory(Pageable pageable, boolean passed);
    List<ClassroomResult> getClassroomResultsByClassroomId(int id);
    Page<Classroom> getTrainerHistory(Pageable pageable, boolean marked);
    Classroom create(ClassroomDTO classroomDTO);
    Classroom createModuleAndClassroom(ModuleClassroomDTO moduleClassroomDTO);
    ClassroomDetailData getClassroomDetail(Integer id);
    Classroom editDetail(Integer id, ClassroomDetailDTO classroomDTO);
    Boolean delete(Integer id);
    Boolean deleteMaterial(Integer id);
    T join(int classroomId);
    List<ClassroomResult> setScore(SetScoreDTO setScoreDTO);
}
