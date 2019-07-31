package com.future.onlinetraining.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomMaterialDTO {
    private Integer id;

    private String description;

    private String file;
}