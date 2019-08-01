package com.future.onlinetraining.entity.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface ModuleData {

    int getId();
    String getName();
    String getDesc();
    double getRating();
    String getTimePerSession();
    String getCategory();
    int getClassroomCount();
    int getOpenClassroomCount();
    int getClosedClassroomCount();
    int getSessionCount();
    boolean getHasExam();
    int getVersion();

}
