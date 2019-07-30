package com.future.onlinetraining.service;

import com.future.onlinetraining.dto.ClassroomDTO;
import com.future.onlinetraining.dto.ModuleClassroomDTO;
import com.future.onlinetraining.entity.Classroom;
import com.future.onlinetraining.entity.Module;
import com.future.onlinetraining.entity.projection.ClassroomData;
import com.future.onlinetraining.entity.projection.ClassroomSubscribed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassroomService {

    Page<Classroom> getAllPageableClassroom();
    Page<Classroom> getAll(Pageable pageable);
    Page<ClassroomSubscribed> getAllSubscribed(int page, int size);
    Page<ClassroomData> all(String name, Boolean hasExam, Pageable pageable);

    Classroom create(ClassroomDTO classroomDTO);
    Classroom createModuleAndClassroom(ModuleClassroomDTO moduleClassroomDTO);
}
