package com.future.onlinetraining.entity.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomRequestsData {

    String className;
    int classId;
    String trainerName;
    long requesterCount;

}
