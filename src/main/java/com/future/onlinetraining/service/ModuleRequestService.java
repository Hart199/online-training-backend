package com.future.onlinetraining.service;

import com.future.onlinetraining.dto.ModuleRequestDTO;
import com.future.onlinetraining.dto.ModuleRequestLikeDTO;
import com.future.onlinetraining.entity.ModuleRequest;
import com.future.onlinetraining.entity.ModuleRequestLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ModuleRequestService {
    Page<ModuleRequest> getAll(Pageable pageable, String name);
    ModuleRequest store(ModuleRequestDTO moduleRequestDTO);
    <T> T voteLike(ModuleRequestLikeDTO moduleRequestLikeDTO);
    ModuleRequest changeStatus(Integer id, String status);
}
