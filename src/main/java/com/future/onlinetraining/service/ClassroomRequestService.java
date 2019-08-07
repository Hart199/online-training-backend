package com.future.onlinetraining.service;

import com.future.onlinetraining.dto.ClassroomRequestDTO;
import com.future.onlinetraining.entity.ClassroomRequest;
import com.future.onlinetraining.entity.projection.ClassroomRequestsData;
import org.junit.internal.requests.ClassRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;

public interface ClassroomRequestService {

    Page<ClassroomRequestsData> getAll(Pageable pageable);
    ClassroomRequest request(ClassroomRequestDTO classroomRequestDTO);
    ClassroomRequest editStatus(Integer id, String status);
    Page<ClassroomRequestsData> getAllByTrainer(Pageable pageable, String name);
}
