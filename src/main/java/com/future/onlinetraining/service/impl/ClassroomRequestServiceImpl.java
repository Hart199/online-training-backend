package com.future.onlinetraining.service.impl;

import com.future.onlinetraining.dto.ClassroomRequestDTO;
import com.future.onlinetraining.entity.Classroom;
import com.future.onlinetraining.entity.ClassroomRequest;
import com.future.onlinetraining.entity.projection.ClassroomRequestsData;
import com.future.onlinetraining.repository.ClassroomRepository;
import com.future.onlinetraining.repository.ClassroomRequestRepository;
import com.future.onlinetraining.service.ClassroomRequestService;
import com.future.onlinetraining.users.model.User;
import com.future.onlinetraining.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service("classroomRequestService")
public class ClassroomRequestServiceImpl implements ClassroomRequestService {

    @Autowired
    private ClassroomRequestRepository classroomRequestRepository;
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private UserService userService;

    public Page<ClassroomRequestsData> getAll(Pageable pageable, String name) {
        Page<ClassroomRequestsData> classroomRequestsDataPage = classroomRequestRepository.getAll(pageable, name);
        classroomRequestsDataPage.getContent().forEach(classroomRequestsData -> {
           classroomRequestsData.setHasVote(this.isHasVote(classroomRequestsData.getClassId()));
        });
        return classroomRequestsDataPage;
    }

    public Page<Classroom> getAllByUser(
            Pageable pageable, String name, String status) {
        User user = userService.getUserFromSession();
        if (user == null)
            throw new NullPointerException("Anda belum login.");

        return classroomRepository.getRequestedUserClassroom(pageable, user.getId(), name, status);
    }

    public Boolean isHasVote(int classId) {
        User user;
        try {
            user = userService.getUserFromSession();
        } catch (NullPointerException e) {
            throw new RuntimeException("Anda belum login");
        }
        ClassroomRequest classroomRequest = classroomRequestRepository
                .findByClassroomIdandUserId(classId, user.getId());
        if (classroomRequest == null)
            return false;
        return true;
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

    @Transactional
    public void reject (Integer classroomRefId) {
        classroomRequestRepository.deleteAllByClassroomId(classroomRefId);
    }

    public Page<ClassroomRequestsData> getAllByTrainer(Pageable pageable, String name) {
        User user = userService.getUserFromSession();
        if (user == null)
            throw new NullPointerException("Anda belum login.");

        Page<ClassroomRequestsData> classroomRequestsDataPage = classroomRequestRepository
                .getAllbyTrainerId(pageable, user.getId(), name);
        return classroomRequestsDataPage;
    }
}
