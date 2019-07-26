package com.future.onlinetraining.service;

import com.future.onlinetraining.dto.ClassroomRequestDTO;
import com.future.onlinetraining.entity.ClassroomRequest;
import com.future.onlinetraining.entity.projection.ClassroomRequestsData;
import org.junit.internal.requests.ClassRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassroomRequestService {

    Page<ClassroomRequestsData> getAll(Pageable pageable);
    ClassroomRequest request(ClassroomRequestDTO classroomRequestDTO);
}
