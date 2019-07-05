package com.future.onlinetraining.service.impl;

import com.future.onlinetraining.entity.projection.TrainerData;
import com.future.onlinetraining.repository.TrainerRepository;
import com.future.onlinetraining.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("trainerService")
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    TrainerRepository trainerRepository;

    public Page<TrainerData> getTopTrainers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return trainerRepository.getTopTrainers(pageable);
    }


}
