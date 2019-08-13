package com.future.onlinetraining.dto;

import com.future.onlinetraining.entity.ClassroomMaterial;
import com.future.onlinetraining.entity.ClassroomSession;
import io.swagger.models.auth.In;
import javafx.scene.control.IndexRange;
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
public class ClassroomDetailDTO {

    List<ClassroomSession> classroomSessions;

    @NotEmpty
    @Size(min = 6, max = 32)
    String name;

    @NotEmpty
    @Size(min = 6, max = 32)
    String trainerEmail;

    @NotEmpty
    String status;

    @NotNull
    Integer minMember;

    @NotNull
    Integer maxMember;
}
