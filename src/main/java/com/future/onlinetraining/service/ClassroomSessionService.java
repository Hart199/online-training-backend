package com.future.onlinetraining.service;

import com.future.onlinetraining.entity.ClassroomSession;
import com.future.onlinetraining.entity.Module;

import java.util.List;

public interface ClassroomSessionService {
    void verifyClassroomSessionOnModule(Module module, List<ClassroomSession> classroomSessions);
}
