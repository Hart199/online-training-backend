package com.future.onlinetraining.service.impl;

import com.future.onlinetraining.dto.ModuleRequestDTO;
import com.future.onlinetraining.dto.ModuleRequestLikeDTO;
import com.future.onlinetraining.entity.ModuleCategory;
import com.future.onlinetraining.entity.ModuleRequest;
import com.future.onlinetraining.entity.ModuleRequestLike;
import com.future.onlinetraining.entity.enumerator.ErrorEnum;
import com.future.onlinetraining.entity.enumerator.ModuleRequestStatus;
import com.future.onlinetraining.entity.projection.ModuleRequestData;
import com.future.onlinetraining.repository.ModuleCategoryRepository;
import com.future.onlinetraining.repository.ModuleRequestLikeRepository;
import com.future.onlinetraining.repository.ModuleRequestRepository;
import com.future.onlinetraining.service.ModuleRequestService;
import com.future.onlinetraining.entity.User;
import com.future.onlinetraining.service.UserService;
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

    public Page<ModuleRequestData> getAll(Pageable pageable, String name) {
        Page<ModuleRequestData> moduleRequestData =  moduleRequestRepository.findAllByNameParam(pageable, name);
        moduleRequestData.getContent().forEach(data -> {
            data.setHasVote(this.isHasVote(data.getModuleRequest().getId()));
        });
        return moduleRequestData;
    }

    public Page<ModuleRequestData> getAllByUser(Pageable pageable, String name, String status) {
        User user = userService.getUserFromSession();

        Page<ModuleRequestData> moduleRequestData = moduleRequestRepository
                .findAllByUser(pageable, user.getId(), name, status);

        moduleRequestData.getContent().forEach(data -> {
            data.setHasVote(this.isHasVote(data.getModuleRequest().getId()));
        });
        return moduleRequestData;
    }

    public Boolean isHasVote(int moduleRequestId) {
        User user = userService.getUserFromSession();

        ModuleRequestLike moduleRequestLike = moduleRequestLikeRepository
                .findByIdAndUserId(moduleRequestId, user.getId());

        return moduleRequestLike == null ? false : true;
    }

    public ModuleRequest store(ModuleRequestDTO moduleRequestDTO) {
        ModuleCategory moduleCategory = moduleCategoryRepository.findByName(moduleRequestDTO.getCategory());
        if (moduleCategory == null)
            throw new RuntimeException(ErrorEnum.MODULE_CATEGORY_NOT_FOUND.getMessage());

        ModuleRequest moduleRequest = ModuleRequest.builder()
                .title(moduleRequestDTO.getTitle())
                .user(userService.getUserFromSession())
                .status(ModuleRequestStatus.WAITING.getStatus())
                .moduleCategory(moduleCategory)
                .build();

        moduleRequest = moduleRequestRepository.save(moduleRequest);

        ModuleRequestLikeDTO moduleRequestLikeDTO = new ModuleRequestLikeDTO();
        moduleRequestLikeDTO.setModuleRequestId(moduleRequest.getId());
        this.voteLike(moduleRequestLikeDTO);

        return moduleRequest;
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
            throw new RuntimeException(ErrorEnum.MODULE_REQUEST_NOT_FOUND.getMessage());

        moduleRequest.get().setStatus(status);
        return moduleRequestRepository.save(moduleRequest.get());
    }
}
