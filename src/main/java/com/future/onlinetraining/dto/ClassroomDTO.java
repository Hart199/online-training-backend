package com.future.onlinetraining.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomDTO {
    private String name;
    private int moduleId;
    private int minMember;
    private int maxMember;
    private Integer refClassroomId;
}
