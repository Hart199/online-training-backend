package com.future.onlinetraining.dto;

import com.future.onlinetraining.entity.ClassroomResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetScoreDTO {
    @NotNull
    int classroomId;

    @NotNull
    @Min(0)
    double minScore;

    List<ClassroomResult> classroomResultList;
}
