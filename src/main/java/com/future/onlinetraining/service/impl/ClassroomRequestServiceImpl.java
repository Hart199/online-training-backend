package com.future.onlinetraining.service.impl;

import com.future.onlinetraining.entity.ClassroomRequest;
import com.future.onlinetraining.entity.projection.ClassroomRequestsData;
import com.future.onlinetraining.repository.ClassroomRequestRepository;
import com.future.onlinetraining.service.ClassroomRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("classroomRequestService")
public class ClassroomRequestServiceImpl implements ClassroomRequestService {

    @Autowired
    private ClassroomRequestRepository classroomRequestRepository;

    public Page<ClassroomRequestsData> getAll(Pageable pageable) {
        return classroomRequestRepository.getAll(pageable);
    }

}
