package com.future.onlinetraining.dto;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateModuleDTO {

    @NotEmpty
    @Size(min = 6, max = 32)
    String name;

    @NotEmpty
    @Size(min = 6)
    String description;

    @NotEmpty
    @Size(min = 6)
    String materialDescription;

    @NotEmpty
    String status;

    @NotEmpty
    String moduleCategory;

    @NotNull
    Boolean hasExam;

    @NotNull
    @Min(30)
    Integer timePerSession;
}
