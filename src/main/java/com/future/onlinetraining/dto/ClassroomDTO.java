package com.future.onlinetraining.dto;

import com.future.onlinetraining.entity.ClassroomSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomDTO {
    private String name;
    private int moduleId;
    private int minMember;
    private int maxMember;
    private int minScore;
    List<ClassroomSession> classroomSessions;
    private Integer refClassroomId;
}
