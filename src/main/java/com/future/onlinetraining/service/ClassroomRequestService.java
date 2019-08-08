package com.future.onlinetraining.service;

import com.future.onlinetraining.dto.ClassroomRequestDTO;
import com.future.onlinetraining.entity.Classroom;
import com.future.onlinetraining.entity.ClassroomRequest;
import com.future.onlinetraining.entity.projection.ClassroomRequestsData;
import org.junit.internal.requests.ClassRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;

public interface ClassroomRequestService {

    Page<ClassroomRequestsData> getAll(Pageable pageable, String name);
    ClassroomRequest request(ClassroomRequestDTO classroomRequestDTO);
    ClassroomRequest editStatus(Integer id, String status);
    Page<ClassroomRequestsData> getAllByTrainer(Pageable pageable, String name);
    Page<Classroom> getAllByUser(Pageable pageable, String name, String status);
}
