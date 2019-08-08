package com.future.onlinetraining.service;

import com.future.onlinetraining.dto.RatingDTO;
import com.future.onlinetraining.entity.TrainerRating;
import com.future.onlinetraining.entity.projection.TrainerData;
import com.future.onlinetraining.users.model.User;
import org.springframework.data.domain.Page;

public interface TrainerService {

    Page<TrainerData> getTopTrainers(int page, int size);
    TrainerRating rate(int trainerId, RatingDTO ratingDTO);
}
