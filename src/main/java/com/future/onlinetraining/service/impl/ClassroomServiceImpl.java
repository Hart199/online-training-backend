package com.future.onlinetraining.service.impl;

import com.future.onlinetraining.dto.*;
import com.future.onlinetraining.entity.*;
import com.future.onlinetraining.entity.projection.ClassroomData;
import com.future.onlinetraining.entity.projection.ClassroomDetailData;
import com.future.onlinetraining.repository.*;
import com.future.onlinetraining.service.ClassroomRequestService;
import com.future.onlinetraining.service.ClassroomService;
import com.future.onlinetraining.service.FileHandlerService;
import com.future.onlinetraining.users.model.User;
import com.future.onlinetraining.users.repository.UserRepository;
import com.future.onlinetraining.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.constraints.Null;
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

    @Override
    public Page<Classroom> getAllPageableClassroom() {
        return classroomRepository.findAll(PageRequest.of(0,5));
    }

    public Page<Classroom> getAllSubscribed(int page, int size, String status) {
        Page<Classroom> classroom = classroomRepository.findSubscribed(
                    PageRequest.of(page, size), userService.getUserFromSession().getId(), status);

        if (classroom == null)
            return null;
        return classroom;
    }

    public Page<Classroom> getAll(Pageable pageable) {
        return classroomRepository.findAll(pageable);
    }

    public Page<ClassroomData> all(String name, Boolean hasExam, Pageable pageable) {
        return classroomRepository.all(pageable, name, hasExam);
    }

    public void verifyClassroomSessionOnModule(Module module, List<ClassroomSession> classroomSessions) {
        if (classroomSessions.size() != module.getTotalSession())
            throw new RuntimeException("Jumlah sesi pada kelas harus sama dengan jumlah sesi pada modul.");

        boolean hasExam = false;
        for(ClassroomSession classroomSession : classroomSessions) {
            hasExam = classroomSession.isExam() ? true : hasExam;
        }

        if (module.isHasExam() != hasExam)
            throw new RuntimeException("Opsi ujian harus sama dengan modul.");
    }

    @Transactional
    public Classroom create(ClassroomDTO classroomDTO) {
        User user = userService.getUserFromSession();

        Module module = moduleRepository.getOne(classroomDTO.getModuleId());

        if (module == null)
            return null;

        verifyClassroomSessionOnModule(module, classroomDTO.getClassroomSessions());

        List<ClassroomSession> classroomSessions = classroomSessionRepository.saveAll(classroomDTO.getClassroomSessions());

        Classroom classroom = Classroom
                .builder()
                .name(classroomDTO.getName())
                .status("open")
                .module(module)
                .min_member(classroomDTO.getMinMember())
                .max_member(classroomDTO.getMaxMember())
                .classroomSessions(classroomSessions)
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

    @Transactional
    public Classroom createModuleAndClassroom(ModuleClassroomDTO moduleClassroomDTO) {
        ModuleCategory moduleCategory = moduleCategoryRepository.findByName(moduleClassroomDTO.getModule().getModuleCategory());
        if (moduleCategory == null)
            return null;

        Optional<ModuleRequest> moduleRequest = Optional.empty();
        if (moduleClassroomDTO.getModuleRequestId() != null)
            moduleRequest = moduleRequestRepository.findById(moduleClassroomDTO.getModuleRequestId());

        User trainer = userRepository.findByEmail(moduleClassroomDTO.getClassroom().getTrainerEmail());
        if (trainer == null)
            return null;

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
        verifyClassroomSessionOnModule(module, moduleClassroomDTO.getClassroom().getClassroomSessions());

        classroomSessions = classroomSessionRepository.saveAll(classroomSessions);

        if (moduleRequest.isPresent()) {
            moduleRequest.get().setStatus("accepted");
            moduleRequestRepository.save(moduleRequest.get());
        }

        Classroom classroom = Classroom
                .builder()
                .module(module)
                .trainer(trainer)
                .max_member(moduleClassroomDTO.getClassroom().getMaxMember())
                .min_member(moduleClassroomDTO.getClassroom().getMinMember())
                .status(moduleClassroomDTO.getClassroom().getStatus())
                .name(moduleClassroomDTO.getClassroom().getName())
                .classroomSessions(classroomSessions)
                .build();

        return classroomRepository.save(classroom);
    }

    public ClassroomDetailData getClassroomDetail(Integer id) {
        ClassroomDetailData classroom = classroomRepository.getDetail(id);

        if (classroom == null)
            return  null;

        return classroom;
    }

    @Transactional
    public Classroom editDetail(Integer id, ClassroomDetailDTO classroomDTO) {
        Classroom classroom = classroomRepository.find(id);
        if (classroom == null)
            return null;

        User trainer = userRepository.findByEmail(classroomDTO.getTrainerEmail());
        if (trainer == null)
            return  null;

        classroom.setName(classroomDTO.getName());
        classroom.setTrainer(trainer);
        classroom.setStatus(classroomDTO.getStatus());
        classroom.setMin_member(classroomDTO.getMinMember());
        classroom.setMax_member(classroomDTO.getMaxMember());

        return classroomRepository.save(classroom);
    }

    public T join(int classroomId) {
        User user = userService.getUserFromSession();
        if (user == null)
            throw new NullPointerException("Anda belum login.");

        Optional<Classroom> classroomOptional = classroomRepository.findById(classroomId);
        if (!classroomOptional.isPresent())
            throw new NullPointerException("Kelas tidak ditemukan.");

        Optional<ClassroomResult> classroomResultOptional = classroomResultRepository
                .findByUserIdAndClassroomId(user.getId(), classroomId);
        if (classroomResultOptional.isPresent())
            throw new RuntimeException("Anda sudah mengikuti kelas.");

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
            return false;

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
        if (user == null)
            throw new NullPointerException("Anda belum login.");
        Page<Classroom> trainerClassrooms = classroomRepository.getTrainerClassrooms(pageable, user.getId(), status);
        if (trainerClassrooms.getContent() == null)
            return null;
        return trainerClassrooms;
    }

    public Page<ClassroomResult> getClassroomHistory(Pageable pageable, boolean passed) {
        if (passed)
            return classroomResultRepository.getPassed(pageable, userService.getUserFromSession().getId());
        return classroomResultRepository.getPassed(pageable, userService.getUserFromSession().getId());
    }

    @Transactional
    public List<ClassroomResult> setScore(SetScoreDTO setScoreDTO) {
        Optional<Classroom> classroomOptional = classroomRepository.findById(setScoreDTO.getClassroomId());
        if (!classroomOptional.isPresent())
            throw new NullPointerException("Kelas tidak ditemukan");

        Classroom classroom = classroomOptional.get();
        classroom.setMinScore(setScoreDTO.getMinScore());
        classroom.setStatus("closed");
        classroomRepository.save(classroom);

        List<ClassroomResult> classroomResultList = new ArrayList<>();
        for (ClassroomResult classroomResultDTO : setScoreDTO.getClassroomResultList()) {
            Optional<ClassroomResult> classroomResultOptional = classroomResultRepository
                    .findById(classroomResultDTO.getId());
            if (!classroomResultOptional.isPresent())
                continue;

            ClassroomResult classroomResult = classroomResultOptional.get();
            classroomResult.setScore(classroomResultDTO.getScore());
            classroomResult.setStatus("finished");
            classroomResultList.add(classroomResult);
        }

        return classroomResultRepository.saveAll(classroomResultList);
    }
}
