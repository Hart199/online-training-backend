package com.future.onlinetraining.dto;

import com.future.onlinetraining.entity.ClassroomMaterial;
import com.future.onlinetraining.entity.ClassroomSession;
import io.swagger.models.auth.In;
import javafx.scene.control.IndexRange;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomDetailDTO {

    List<ClassroomSession> classroomSessions;
    String name;
    String trainerEmail;
    String status;
    Integer minMember;
    Integer maxMember;
}
