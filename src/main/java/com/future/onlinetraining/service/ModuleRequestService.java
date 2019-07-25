package com.future.onlinetraining.service;

import com.future.onlinetraining.entity.ModuleRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ModuleRequestService {
    Page<ModuleRequest> getAll(Pageable pageable, String name);
}
