package com.future.onlinetraining.service;

import com.future.onlinetraining.entity.Classroom;
import com.future.onlinetraining.entity.projection.ClassroomSubscribed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassroomService {

    Page<Classroom> getAllPageableClassroom();
    Page<Classroom> getAll(Pageable pageable);
    Page<ClassroomSubscribed> getAllSubscribed(int page, int size);

}
