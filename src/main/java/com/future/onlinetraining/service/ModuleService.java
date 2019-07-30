package com.future.onlinetraining.service;

import com.future.onlinetraining.dto.DeleteModuleCategoryDTO;
import com.future.onlinetraining.dto.UpdateModuleCategoryDTO;
import com.future.onlinetraining.dto.UpdateModuleDTO;
import com.future.onlinetraining.entity.Module;
import com.future.onlinetraining.entity.ModuleCategory;
import com.future.onlinetraining.entity.ModuleRating;
import com.future.onlinetraining.entity.projection.ModuleData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ModuleService {

//    Page<ModuleData> getAll(Pageable pageable);
    Page<ModuleRating> getRatings(int id, Pageable pageable);
    Page<ModuleData> getAllBySearchTerm(
            Pageable pageable, String name,
            String category, Boolean hasExam);
    Page<ModuleCategory> getAllModuleCategory(Pageable pageable);
    ModuleCategory addModuleCategory(ModuleCategory moduleCategory);
    ModuleCategory updateModuleCategory(UpdateModuleCategoryDTO updateModuleCategoryDTO);
    boolean deleteModuleCategory(DeleteModuleCategoryDTO deleteModuleCategoryDTO);
    Module getOne(Integer id);
    Module editModule(Integer id, UpdateModuleDTO updateModuleDTO);
    boolean deleteModule(Integer id);
}
