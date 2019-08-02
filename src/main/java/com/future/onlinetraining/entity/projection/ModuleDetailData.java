package com.future.onlinetraining.entity.projection;

import com.future.onlinetraining.entity.Module;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleDetailData {
    Module module;
    Double moduleRating;
    boolean hasExam;
}
