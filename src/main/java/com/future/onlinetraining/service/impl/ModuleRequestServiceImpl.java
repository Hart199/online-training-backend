package com.future.onlinetraining.service.impl;

import com.future.onlinetraining.entity.ModuleRequest;
import com.future.onlinetraining.repository.ModuleRequestRepository;
import com.future.onlinetraining.service.ModuleRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("moduleRequestService")
public class ModuleRequestServiceImpl implements ModuleRequestService {

    @Autowired
    ModuleRequestRepository moduleRequestRepository;

    public Page<ModuleRequest> getAll(Pageable pageable, String name) {
        return moduleRequestRepository.findAllByNameParam(pageable, name);
    }
}
