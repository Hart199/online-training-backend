package com.future.onlinetraining.dto;

import com.future.onlinetraining.entity.ClassroomResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetScoreDTO {
    @NotEmpty
    int classroomId;

    @NotEmpty
    @Min(0)
    double minScore;

    List<ClassroomResult> classroomResultList;
}
