package com.future.onlinetraining.repository;

import com.future.onlinetraining.entity.TrainerRating;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainerRatingRepository extends JpaRepository<TrainerRating, Integer> {
    Optional<TrainerRating> findByUserIdAndTrainerId(int userId, int trainerId);
}
