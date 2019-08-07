package com.future.onlinetraining.entity.projection;

import com.future.onlinetraining.entity.Classroom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllModuleData {
    int id;
    String name;
    String desc;
    Double rating;
    Integer timePerSession;
    String category;
    Long classroomCount;
    Long sessionCount;
    Boolean hasExam;
    Integer version;
}
