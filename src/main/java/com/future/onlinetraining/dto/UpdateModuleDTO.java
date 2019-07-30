package com.future.onlinetraining.dto;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateModuleDTO {

    String name;
    String description;
    String materialDescription;
    String status;
    String moduleCategory;
    Boolean hasExam;
    Integer timePerSession;
}
