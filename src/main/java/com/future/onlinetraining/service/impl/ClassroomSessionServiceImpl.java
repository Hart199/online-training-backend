package com.future.onlinetraining.service.impl;

import com.future.onlinetraining.entity.ClassroomSession;
import com.future.onlinetraining.entity.Module;
import com.future.onlinetraining.entity.enumerator.ErrorEnum;
import com.future.onlinetraining.service.ClassroomSessionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("classroomSessionService")
public class ClassroomSessionServiceImpl implements ClassroomSessionService {
    public void verifyClassroomSessionOnModule(Module module, List<ClassroomSession> classroomSessions) {
        if (classroomSessions.size() != module.getTotalSession())
            throw new RuntimeException(ErrorEnum.CLASSROOM_SESSION_VALIDATION_ERROR.getMessage());

        boolean hasExam = false;
        for(ClassroomSession classroomSession : classroomSessions) {
            hasExam = classroomSession.isExam() ? true : hasExam;
        }

        if (module.isHasExam() != hasExam)
            throw new RuntimeException(ErrorEnum.EXAM_VALIDATION_ERROR.getMessage());
    }
}
