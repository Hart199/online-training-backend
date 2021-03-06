package com.future.onlinetraining.service;

import com.future.onlinetraining.dto.RatingDTO;
import com.future.onlinetraining.dto.DeleteModuleCategoryDTO;
import com.future.onlinetraining.dto.UpdateModuleCategoryDTO;
import com.future.onlinetraining.dto.UpdateModuleDTO;
import com.future.onlinetraining.entity.Module;
import com.future.onlinetraining.entity.ModuleCategory;
import com.future.onlinetraining.entity.ModuleRating;
import com.future.onlinetraining.entity.projection.GetAllModuleData;
import com.future.onlinetraining.entity.projection.ModuleDetailData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ModuleService {

    Page<ModuleRating> getRatings(int id, Pageable pageable);
    ModuleRating addRating(int id, RatingDTO ratingDTO);
    Page<Module> getAllBySearchTerm(
            Pageable pageable, String name,
            String category, Boolean hasExam);
    Page<ModuleCategory> getAllModuleCategory(Pageable pageable);
    ModuleCategory addModuleCategory(ModuleCategory moduleCategory);
    ModuleCategory updateModuleCategory(UpdateModuleCategoryDTO updateModuleCategoryDTO);
    boolean deleteModuleCategory(DeleteModuleCategoryDTO deleteModuleCategoryDTO);
    Module getOne(Integer id);
    Module editModule(Integer id, UpdateModuleDTO updateModuleDTO);
    boolean deleteModule(Integer id);
    ModuleDetailData getModuleDetail(Integer id);
}
