package com.future.onlinetraining.dto;

import com.future.onlinetraining.entity.ModuleCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateModuleCategoryDTO {
    ModuleCategory moduleCategory;
    String newCategoryName;
}
