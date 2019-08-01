package com.future.onlinetraining.service.impl;

import com.future.onlinetraining.dto.ModuleRequestDTO;
import com.future.onlinetraining.dto.ModuleRequestLikeDTO;
import com.future.onlinetraining.entity.ModuleCategory;
import com.future.onlinetraining.entity.ModuleRequest;
import com.future.onlinetraining.entity.ModuleRequestLike;
import com.future.onlinetraining.repository.ModuleCategoryRepository;
import com.future.onlinetraining.repository.ModuleRequestLikeRepository;
import com.future.onlinetraining.repository.ModuleRequestRepository;
import com.future.onlinetraining.service.ModuleRequestService;
import com.future.onlinetraining.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("moduleRequestService")
public class ModuleRequestServiceImpl implements ModuleRequestService {

    @Autowired
    ModuleRequestRepository moduleRequestRepository;
    @Autowired
    ModuleCategoryRepository moduleCategoryRepository;
    @Autowired
    ModuleRequestLikeRepository moduleRequestLikeRepository;
    @Autowired
    UserService userService;

    public Page<ModuleRequest> getAll(Pageable pageable, String name) {
        return moduleRequestRepository.findAllByNameParam(pageable, name);
    }

    public ModuleRequest store(ModuleRequestDTO moduleRequestDTO) {
        ModuleCategory moduleCategory = moduleCategoryRepository.findByName(moduleRequestDTO.getCategory());
        if (moduleCategory == null)
            return null;

        ModuleRequest moduleRequest = ModuleRequest.builder()
                .title(moduleRequestDTO.getTitle())
                .user(userService.getUserFromSession())
                .status("waiting")
                .moduleCategory(moduleCategory)
                .build();

        return moduleRequestRepository.save(moduleRequest);
    }

    public <T> T voteLike(ModuleRequestLikeDTO moduleRequestLikeDTO) {
        ModuleRequestLike moduleRequestLike = moduleRequestLikeRepository.findByIdAndUserId(
                moduleRequestLikeDTO.getModuleRequestId(), userService.getUserFromSession().getId());

        if (moduleRequestLike != null) {
            moduleRequestLikeRepository.delete(moduleRequestLike);
            return null;
        }

        ModuleRequest moduleRequest = moduleRequestRepository.getOne(moduleRequestLikeDTO.getModuleRequestId());
        return (T) moduleRequestLikeRepository.save(
                ModuleRequestLike.builder()
                .moduleRequest(moduleRequest)
                .user(userService.getUserFromSession())
                .build()
        );
    }

    public ModuleRequest changeStatus(Integer id, String status) {
        Optional<ModuleRequest> moduleRequest = moduleRequestRepository.findById(id);
        if (!moduleRequest.isPresent())
            return null;

        moduleRequest.get().setStatus(status);
        return moduleRequestRepository.save(moduleRequest.get());
    }
}
