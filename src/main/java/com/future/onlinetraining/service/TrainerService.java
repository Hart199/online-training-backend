package com.future.onlinetraining.service;

import com.future.onlinetraining.dto.RatingDTO;
import com.future.onlinetraining.entity.TrainerRating;
import com.future.onlinetraining.entity.projection.TrainerData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TrainerService {

    Page<TrainerData> getTopTrainers(int page, int size);
    TrainerRating rate(int trainerId, RatingDTO ratingDTO);
    Page<TrainerRating> getRatings(Pageable pageable, int id);
}
