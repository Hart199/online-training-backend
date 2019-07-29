package com.future.onlinetraining.service.impl;

import com.future.onlinetraining.dto.ClassroomDTO;
import com.future.onlinetraining.entity.Classroom;
import com.future.onlinetraining.entity.ClassroomRequest;
import com.future.onlinetraining.entity.ClassroomResult;
import com.future.onlinetraining.entity.Module;
import com.future.onlinetraining.entity.projection.ClassroomData;
import com.future.onlinetraining.entity.projection.ClassroomSubscribed;
import com.future.onlinetraining.repository.ClassroomRepository;
import com.future.onlinetraining.repository.ClassroomRequestRepository;
import com.future.onlinetraining.repository.ClassroomResultRepository;
import com.future.onlinetraining.repository.ModuleRepository;
import com.future.onlinetraining.service.ClassroomService;
import com.future.onlinetraining.users.model.User;
import com.future.onlinetraining.users.repository.UserRepository;
import com.future.onlinetraining.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("classroomService")
public class ClassroomServiceImpl implements ClassroomService {

    @Autowired
    ClassroomRepository classroomRepository;
    @Autowired
    ClassroomRequestRepository classroomRequestRepository;
    @Autowired
    ModuleRepository moduleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ClassroomResultRepository classroomResultRepository;
    @Autowired
    UserService userService;

    @Override
    public Page<Classroom> getAllPageableClassroom() {
        return classroomRepository.findAll(PageRequest.of(0,5));
    }

    public Page<ClassroomSubscribed> getAllSubscribed(int page, int size) {
        return classroomRepository.findSubscribed(
                PageRequest.of(page, size), userService.getUserFromSession().getId());
    }

    public Page<Classroom> getAll(Pageable pageable) {
        return classroomRepository.findAll(pageable);
    }

    public Page<ClassroomData> all(String name, Boolean hasExam) {
        return classroomRepository.all(PageRequest.of(0,5), name, hasExam);
    }

    @Transactional
    public Classroom create(ClassroomDTO classroomDTO) {
        User user = userService.getUserFromSession();

        Module module = moduleRepository.getOne(classroomDTO.getModuleId());

        if (module == null)
            return null;

        Classroom classroom = Classroom
                .builder()
                .name(classroomDTO.getName())
                .status("open")
                .module(module)
                .min_member(classroomDTO.getMinMember())
                .max_member(classroomDTO.getMaxMember())
                .trainer(user)
                .build();

        classroom = classroomRepository.save(classroom);

        if (classroomDTO.getRefClassroomId() != null) {
            List<ClassroomRequest> classroomRequestList;
            classroomRequestList = classroomRequestRepository.findAllByClassroomId(classroomDTO.getRefClassroomId());

            List<ClassroomResult> chosenClassroomResultList = new ArrayList<>();
            int currentMember = 0;
            for (ClassroomRequest classroomRequest : classroomRequestList) {
                if (currentMember >= classroomDTO.getMaxMember())
                    break;

                ClassroomResult classroomResult = ClassroomResult
                        .builder()
                        .user(classroomRequest.getUser())
                        .status("waiting")
                        .classroom(classroom)
                        .score(0)
                        .build();

                chosenClassroomResultList.add(classroomResult);

                classroomRequest.setStatus("accepted");
                classroomRequestRepository.save(classroomRequest);
            }

            if (!chosenClassroomResultList.isEmpty())
                classroomResultRepository.saveAll(chosenClassroomResultList);
        }

        return classroom;
    }


//    public Page<C>
}
