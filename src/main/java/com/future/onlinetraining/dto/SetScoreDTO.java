package com.future.onlinetraining.dto;

import com.future.onlinetraining.entity.ClassroomResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetScoreDTO {
    int classroomId;
    double minScore;
    List<ClassroomResult> classroomResultList;
}
