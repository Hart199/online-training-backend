package com.future.onlinetraining.service.impl;

import com.future.onlinetraining.dto.*;
import com.future.onlinetraining.entity.*;
import com.future.onlinetraining.entity.enumerator.ClassroomStatus;
import com.future.onlinetraining.entity.enumerator.ErrorEnum;
import com.future.onlinetraining.entity.enumerator.ModuleRequestStatus;
import com.future.onlinetraining.entity.projection.ClassroomData;
import com.future.onlinetraining.entity.projection.ClassroomDetailData;
import com.future.onlinetraining.repository.*;
import com.future.onlinetraining.service.*;
import com.future.onlinetraining.entity.User;
import com.future.onlinetraining.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("classroomService")
public class ClassroomServiceImpl<T> implements ClassroomService {

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
    ModuleCategoryRepository moduleCategoryRepository;
    @Autowired
    ClassroomSessionRepository classroomSessionRepository;
    @Autowired
    FileHandlerService fileHandlerService;
    @Autowired
    ClassroomMaterialRepository classroomMaterialRepository;
    @Autowired
    ModuleRequestRepository moduleRequestRepository;
    @Autowired
    UserService userService;
    @Autowired
    ClassroomRequestService classroomRequestService;
    @Autowired
    ClassroomSessionService classroomSessionService;

    @Override
    public Page<Classroom> getAllPageableClassroom() {
        return classroomRepository.findAll(PageRequest.of(0,5));
    }

    public Page<Classroom> getAllSubscribed(int page, int size) {
        return classroomRepository.findSubscribed(
                    PageRequest.of(page, size), userService.getUserFromSession().getId());
    }

    public Page<Classroom> getAll(Pageable pageable) {
        Page<Classroom> classroomPage = classroomRepository.findAll(pageable);
        return classroomPage;
    }

    public Page<ClassroomData> all(String name, Boolean hasExam, Pageable pageable) {
        Page<ClassroomData> classroomDataPage = classroomRepository.all(pageable, name, hasExam);
        return classroomDataPage;
    }



    @Transactional
    public Classroom create(ClassroomDTO classroomDTO) {
        User user = userService.getUserFromSession();

        Optional<Module> module = moduleRepository.findById(classroomDTO.getModuleId());

        if (!module.isPresent())
            throw new RuntimeException(ErrorEnum.MODULE_NOT_FOUND.getMessage());

        classroomSessionService.verifyClassroomSessionOnModule(module.get(), classroomDTO.getClassroomSessions());

        Classroom classroom = Classroom
                .builder()
                .name(classroomDTO.getName())
                .status(ClassroomStatus.OPEN.getStatus())
                .module(module.get())
                .min_member(classroomDTO.getMinMember())
                .max_member(classroomDTO.getMaxMember())
                .trainer(user)
                .build();

        classroom = classroomRepository.save(classroom);

        for (ClassroomSession classroomSession : classroomDTO.getClassroomSessions()) {
            classroomSession.setClassroom(classroom);
        }
        List<ClassroomSession> classroomSessions = classroomSessionRepository.saveAll(classroomDTO.getClassroomSessions());
        classroom.setClassroomSessions(classroomSessions);

        if (classroomDTO.getRefClassroomId() != null) {
            classroomRequestRepository.deleteAllByClassroomId(classroomDTO.getRefClassroomId());
        }

        return classroom;
    }

    @Transactional
    public Classroom createModuleAndClassroom(ModuleClassroomDTO moduleClassroomDTO) {
        ModuleCategory moduleCategory = moduleCategoryRepository.findByName(moduleClassroomDTO.getModule().getModuleCategory());
        if (moduleCategory == null)
            throw new RuntimeException(ErrorEnum.MODULE_CATEGORY_NOT_FOUND.getMessage());

        Optional<ModuleRequest> moduleRequest = Optional.empty();
        if (moduleClassroomDTO.getModuleRequestId() != null)
            moduleRequest = moduleRequestRepository.findById(moduleClassroomDTO.getModuleRequestId());

        Optional<User> trainer = userRepository.findByEmail(moduleClassroomDTO.getClassroom().getTrainerEmail());
        if (!trainer.isPresent())
            throw new RuntimeException(ErrorEnum.TRAINER_NOT_FOUND.getMessage());

        Module module = Module
                .builder()
                .name(moduleClassroomDTO.getModule().getName())
                .description(moduleClassroomDTO.getModule().getDescription())
                .materialDescription(moduleClassroomDTO.getModule().getMaterialDescription())
                .moduleCategory(moduleCategory)
                .timePerSession(moduleClassroomDTO.getModule().getTimePerSession())
                .status(moduleClassroomDTO.getModule().getStatus())
                .totalSession(moduleClassroomDTO.getModule().getTotalSession())
                .hasExam(moduleClassroomDTO.getModule().isHasExam())
                .version(1)
                .build();

        module = moduleRepository.save(module);

        List<ClassroomSession> classroomSessions = moduleClassroomDTO.getClassroom().getClassroomSessions();
        classroomSessionService.verifyClassroomSessionOnModule(module, moduleClassroomDTO.getClassroom().getClassroomSessions());

        if (moduleRequest.isPresent()) {
            moduleRequest.get().setStatus(ModuleRequestStatus.ACCEPTED.getStatus());
            moduleRequestRepository.save(moduleRequest.get());
        }

        Classroom classroom = Classroom
                .builder()
                .module(module)
                .trainer(trainer.get())
                .max_member(moduleClassroomDTO.getClassroom().getMaxMember())
                .min_member(moduleClassroomDTO.getClassroom().getMinMember())
                .status(moduleClassroomDTO.getClassroom().getStatus())
                .name(moduleClassroomDTO.getClassroom().getName())
                .build();

        for (ClassroomSession classroomSession : classroomSessions) {
            classroomSession.setClassroom(classroom);
        }
        classroomSessionRepository.saveAll(classroomSessions);

        return classroomRepository.save(classroom);
    }

    public ClassroomDetailData getClassroomDetail(Integer id) {
        ClassroomDetailData classroom = classroomRepository.getDetail(id);
        if (classroom == null)
            throw new RuntimeException(ErrorEnum.CLASSROOM_NOT_FOUND.getMessage());

        return classroom;
    }

    @Transactional
    public Classroom editDetail(Integer id, ClassroomDetailDTO classroomDTO) {
        Classroom classroom = classroomRepository.find(id);
        if (classroom == null)
            throw new RuntimeException(ErrorEnum.CLASSROOM_NOT_FOUND.getMessage());

        Optional<User> trainer = userRepository.findByEmail(classroomDTO.getTrainerEmail());
        if (!trainer.isPresent())
            throw new RuntimeException(ErrorEnum.TRAINER_NOT_FOUND.getMessage());

        if (classroomDTO.getClassroomSessions() != null) {
            classroomSessionService.verifyClassroomSessionOnModule(classroom.getModule(), classroomDTO.getClassroomSessions());

            classroomDTO.getClassroomSessions().forEach(classroomSession -> {
                classroomSession.setClassroom(classroom);
            });

            classroomSessionRepository.saveAll(classroomDTO.getClassroomSessions());
        }

        classroom.setName(classroomDTO.getName());
        classroom.setTrainer(trainer.get());
        classroom.setStatus(classroomDTO.getStatus());
        classroom.setMin_member(classroomDTO.getMinMember());
        classroom.setMax_member(classroomDTO.getMaxMember());

        return classroomRepository.save(classroom);
    }

    public T join(int classroomId) {
        User user = userService.getUserFromSession();

        Optional<Classroom> classroomOptional = classroomRepository.findById(classroomId);
        if (!classroomOptional.isPresent())
            throw new NullPointerException(ErrorEnum.CLASSROOM_NOT_FOUND.getMessage());

        Optional<ClassroomResult> classroomResultOptional = classroomResultRepository
                .findByUserIdAndClassroomId(user.getId(), classroomId);
        if (classroomResultOptional.isPresent())
            throw new RuntimeException(ErrorEnum.HAS_JOINED_CLASSROOM.getMessage());

        // Check if classroom is full or not
        // if classroom is full, call request classroom
        if (classroomOptional.get().getClassroomResults().size() >= classroomOptional.get().getMax_member()) {
            ClassroomRequestDTO classroomRequestDTO = new ClassroomRequestDTO();
            classroomRequestDTO.setClassroomId(classroomId);
            return (T) classroomRequestService.request(classroomRequestDTO);
        }

        ClassroomResult classroomResult = ClassroomResult
                .builder()
                .classroom(classroomOptional.get())
                .status("accepted")
                .user(user)
                .build();

        return (T) classroomResultRepository.save(classroomResult);
    }

    public Boolean delete(Integer id) {
        Classroom classroom = classroomRepository.find(id);
        if (classroom == null)
            throw new RuntimeException(ErrorEnum.CLASSROOM_NOT_FOUND.getMessage());

        classroomRepository.deleteById(id);
        return true;
    }

    public Boolean deleteMaterial(Integer id) {
        ClassroomMaterial classroomMaterial = classroomMaterialRepository.find(id);
        if (classroomMaterial == null)
            return false;

        classroomMaterialRepository.deleteById(id);
        return true;
    }

    public Page<Classroom> getTrainerClassrooms(Pageable pageable, String status) {
        User user = userService.getUserFromSession();
        if (status.toLowerCase().equals("available")) {
            return classroomRepository.getAvailableTrainerClassrooms(pageable, user.getId());
        }
        return classroomRepository.getTrainerClassrooms(pageable, user.getId(), status);
    }

    public Page<ClassroomResult> getClassroomHistory(Pageable pageable, boolean passed) {
        User user = userService.getUserFromSession();
        if (passed)
            return classroomResultRepository.getPassed(pageable, user.getId());
        return classroomResultRepository.getNotPassed(pageable, user.getId());
    }

    @Transactional
    public List<ClassroomResult> setScore(SetScoreDTO setScoreDTO) {
        Optional<Classroom> classroomOptional = classroomRepository.findById(setScoreDTO.getClassroomId());
        if (!classroomOptional.isPresent())
            throw new NullPointerException(ErrorEnum.CLASSROOM_NOT_FOUND.getMessage());

        Classroom classroom = classroomOptional.get();
        classroom.setMinScore(setScoreDTO.getMinScore());
        classroom.setStatus(ClassroomStatus.CLOSED.getStatus());
        classroom.setHasFinished(true);
        classroomRepository.save(classroom);

        List<ClassroomResult> classroomResultList = new ArrayList<>();
        for (ClassroomResult classroomResultDTO : setScoreDTO.getClassroomResultList()) {
            Optional<ClassroomResult> classroomResultOptional = classroomResultRepository
                    .findById(classroomResultDTO.getId());
            if (!classroomResultOptional.isPresent())
                continue;

            ClassroomResult classroomResult = classroomResultOptional.get();
            classroomResult.setScore(classroomResultDTO.getScore());
            classroomResult.setStatus("done");
            classroomResultList.add(classroomResult);
        }

        return classroomResultRepository.saveAll(classroomResultList);
    }

    public List<ClassroomResult> getClassroomResultsByClassroomId(int id) {
        return classroomResultRepository.findAllByClassroomId(id);
    }

    public Page<Classroom> getTrainerHistory(Pageable pageable, boolean marked) {
        User user = userService.getUserFromSession();
        Integer userId = user.getRole().getValue().equals("ADMIN") ? null : user.getId();
        if (!marked) {
            return classroomRepository.getNotMarkedTrainerClassroomHistory(pageable, userId);
        }
        return classroomRepository.getMarkedTrainerClassroomHistory(pageable, userId);
    }
}
