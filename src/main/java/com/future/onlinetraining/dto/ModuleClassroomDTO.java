package com.future.onlinetraining.dto;

import com.future.onlinetraining.entity.ClassroomSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleClassroomDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Classroom {
        String name;
        int minMember;
        int maxMember;
        String status;
        String trainerEmail;
        List<ClassroomSession> classroomSessions;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ModuleDTO {
        String name;
        String description;
        int timePerSession;
        String status;
        String moduleCategory;
    }

    Classroom classroom;
    ModuleDTO module;
    Integer moduleRequestId;
}
