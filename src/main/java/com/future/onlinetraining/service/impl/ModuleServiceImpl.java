package com.future.onlinetraining.service.impl;

import com.future.onlinetraining.entity.ModuleCategory;
import com.future.onlinetraining.entity.ModuleRating;
import com.future.onlinetraining.entity.projection.ModuleData;
import com.future.onlinetraining.repository.ModuleCategoryRepository;
import com.future.onlinetraining.repository.ModuleRatingRepository;
import com.future.onlinetraining.repository.ModuleRepository;
import com.future.onlinetraining.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("moduleService")
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    ModuleRepository moduleRepository;
    @Autowired
    ModuleRatingRepository moduleRatingRepository;
    @Autowired
    ModuleCategoryRepository moduleCategoryRepository;

//    public Page<ModuleData> getAll(Pageable pageable) {
//        return moduleRepository.getAllModule(pageable);
//    }

    public Page<ModuleRating> getRatings(int id, Pageable pageable) {
        return moduleRatingRepository.findAllByModuleId(id, pageable);
    }

    public Page<ModuleData> getAllBySearchTerm(
            Pageable pageable, String name, String category, Boolean hasExam) {
        return moduleRepository.getAllBySearhTerm(pageable, name, category, hasExam);
    }

    public Page<ModuleCategory> getAllModuleCategory(Pageable pageable) {
        return moduleCategoryRepository.findAll(pageable);
    }

    public ModuleCategory addModuleCategory(ModuleCategory moduleCategory) {
        ModuleCategory category = moduleCategoryRepository.findByName(moduleCategory.getName());
        if (category != null)
            return null;

        return moduleCategoryRepository.save(moduleCategory);
    }
}
