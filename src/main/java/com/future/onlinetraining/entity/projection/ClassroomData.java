package com.future.onlinetraining.entity.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomData {
    private int id;
    private String name;
    private String moduleName;
    private String trainer;
    private String status;
    private int minMember;
    private int maxMember;
    private long member;
    private long requestCount;
    private double moduleRating;
}
