package com.future.onlinetraining.service;

import com.future.onlinetraining.dto.ModuleRequestDTO;
import com.future.onlinetraining.dto.ModuleRequestLikeDTO;
import com.future.onlinetraining.entity.ModuleRequest;
import com.future.onlinetraining.entity.ModuleRequestLike;
import com.future.onlinetraining.entity.projection.ModuleRequestData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ModuleRequestService {
    Page<ModuleRequestData> getAll(Pageable pageable, String name);
    ModuleRequest store(ModuleRequestDTO moduleRequestDTO);
    <T> T voteLike(ModuleRequestLikeDTO moduleRequestLikeDTO);
    ModuleRequest changeStatus(Integer id, String status);
    Page<ModuleRequestData> getAllByUser(Pageable pageable, String name, String status);
}
