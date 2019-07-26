package com.future.onlinetraining.service.impl;

import com.future.onlinetraining.dto.ClassroomRequestDTO;
import com.future.onlinetraining.entity.ClassroomRequest;
import com.future.onlinetraining.entity.projection.ClassroomRequestsData;
import com.future.onlinetraining.repository.ClassroomRepository;
import com.future.onlinetraining.repository.ClassroomRequestRepository;
import com.future.onlinetraining.service.ClassroomRequestService;
import com.future.onlinetraining.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("classroomRequestService")
public class ClassroomRequestServiceImpl implements ClassroomRequestService {

    @Autowired
    private ClassroomRequestRepository classroomRequestRepository;
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private UserService userService;

    public Page<ClassroomRequestsData> getAll(Pageable pageable) {
        return classroomRequestRepository.getAll(pageable);
    }

    public ClassroomRequest request(ClassroomRequestDTO classroomRequestDTO) {
        ClassroomRequest classroomRequest = classroomRequestRepository
                .findByClassroomIdandUserId(classroomRequestDTO.getClassroomId(), userService.getUserFromSession().getId());

        if (classroomRequest != null) {
            classroomRequestRepository.delete(classroomRequest);
            return null;
        }

        classroomRequest = ClassroomRequest
                .builder()
                .classroom(classroomRepository.getOne(classroomRequestDTO.getClassroomId()))
                .user(userService.getUserFromSession())
                .status("waiting")
                .build();
        return classroomRequestRepository.save(classroomRequest);
    }
}
