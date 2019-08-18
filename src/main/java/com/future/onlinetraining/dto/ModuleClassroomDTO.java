package com.future.onlinetraining.dto;

import com.future.onlinetraining.entity.ClassroomSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

        @NotNull
        @Min(0)
        int minMember;

        @NotNull
        @Min(1)
        int maxMember;

        @NotEmpty
        String status;

        @NotEmpty
        @Size(min = 6, max = 32)
        String trainerEmail;

        @Valid
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
        
        @Min(30)
        int timePerSession;

        @NotEmpty
        String status;

        @NotEmpty
        String moduleCategory;

        String materialDescription;

        @Min(1)
        int totalSession;

        @NotNull
        boolean hasExam;
    }

    @Valid
    Classroom classroom;
    @Valid
    ModuleDTO module;
    Integer moduleRequestId;
}
