package com.future.onlinetraining.dto;

import com.future.onlinetraining.entity.ClassroomSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleClassroomDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Classroom {
        @NotEmpty
        @Size(min = 6, max = 32)
        String name;

        @NotEmpty
        @Min(0)
        int minMember;

        @NotEmpty
        @Min(1)
        int maxMember;

        @NotEmpty
        String status;

        @NotEmpty
        @Size(min = 6, max = 32)
        String trainerEmail;

        List<ClassroomSession> classroomSessions;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ModuleDTO {
        @NotEmpty
        @Size(min = 6, max = 32)
        String name;

        @NotEmpty
        @Size(min = 6, max = 32)
        String description;

        @NotEmpty
        @Min(30)
        int timePerSession;

        @NotEmpty
        String status;

        @NotEmpty
        String moduleCategory;

        String materialDescription;

        @Min(1)
        int totalSession;

        @NotEmpty
        boolean hasExam;
    }

    Classroom classroom;
    ModuleDTO module;
    Integer moduleRequestId;
}
