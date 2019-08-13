package com.future.onlinetraining.dto;

import com.future.onlinetraining.entity.ModuleCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateModuleCategoryDTO {
    ModuleCategory moduleCategory;

    @NotEmpty
    String newCategoryName;
}
