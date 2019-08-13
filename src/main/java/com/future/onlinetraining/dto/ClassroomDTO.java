package com.future.onlinetraining.dto;

import com.future.onlinetraining.entity.ClassroomSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomDTO {
    @NotNull
    @Size(min = 6, max = 32)
    private String name;

    @NotNull
    private int moduleId;

    @NotNull
    private int minMember;

    @NotNull
    private int maxMember;

    List<ClassroomSession> classroomSessions;

    @NotNull
    private Integer refClassroomId;
}
