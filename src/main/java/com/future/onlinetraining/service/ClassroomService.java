package com.future.onlinetraining.service;

import com.future.onlinetraining.entity.Classroom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassroomService {

    public Page<Classroom> getAllPageableClassroom();
    Page<Classroom> getAll(Pageable pageable);

}
