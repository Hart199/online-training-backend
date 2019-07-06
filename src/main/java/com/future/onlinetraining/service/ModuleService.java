package com.future.onlinetraining.service;

import com.future.onlinetraining.entity.Module;
import com.future.onlinetraining.entity.ModuleRating;
import com.future.onlinetraining.entity.projection.ModuleData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ModuleService {

    Page<ModuleData> getAll(Pageable pageable);
    Page<ModuleRating> getRatings(int id, Pageable pageable);
}