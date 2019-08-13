package com.future.onlinetraining.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleRequestDTO {

    @NotEmpty
    @Size(min = 6, max = 32)
    private String title;

    @NotEmpty
    private String category;

}
