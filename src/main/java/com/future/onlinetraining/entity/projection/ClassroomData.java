package com.future.onlinetraining.entity.projection;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomData {
    private Integer id;
    private String name;
    private String moduleName;
    private String trainer;
    private String status;
    private Integer minMember;
    private Integer maxMember;
    private Long member;
    private Long requestCount;
    private Double moduleRating;

//    public void setModuleRating(Double moduleRating) {
//        if (moduleRating == null) {
//            this.moduleRating = (double) 0;
//            return;
//        }
//        this.moduleRating = moduleRating;
//    }
}
