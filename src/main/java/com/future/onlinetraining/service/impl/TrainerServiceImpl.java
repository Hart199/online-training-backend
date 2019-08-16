package com.future.onlinetraining.service.impl;

import com.future.onlinetraining.dto.RatingDTO;
import com.future.onlinetraining.entity.TrainerRating;
import com.future.onlinetraining.entity.enumerator.ErrorEnum;
import com.future.onlinetraining.entity.projection.TrainerData;
import com.future.onlinetraining.repository.TrainerRatingRepository;
import com.future.onlinetraining.repository.TrainerRepository;
import com.future.onlinetraining.service.TrainerService;
import com.future.onlinetraining.entity.User;
import com.future.onlinetraining.repository.UserRepository;
import com.future.onlinetraining.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("trainerService")
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    TrainerRepository trainerRepository;
    @Autowired
    TrainerRatingRepository trainerRatingRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    public Page<TrainerData> getTopTrainers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return trainerRepository.getTopTrainers(pageable);
    }

    public TrainerRating rate(int trainerId, RatingDTO ratingDTO) {
        User user = userService.getUserFromSession();

        Optional<User> trainer = userRepository.findById(trainerId);
        if (!trainer.isPresent())
            throw new NullPointerException(ErrorEnum.TRAINER_NOT_FOUND.getMessage());

        Optional<TrainerRating>  trainerRatingOptional = trainerRatingRepository
                .findByUserIdAndTrainerId(user.getId(), trainerId);
        TrainerRating trainerRating = new TrainerRating();
        if (trainerRatingOptional.isPresent())
            trainerRating = trainerRatingOptional.get();

        trainerRating.setComment(ratingDTO.getComment());
        trainerRating.setValue(ratingDTO.getValue());
        trainerRating.setUser(user);
        trainerRating.setTrainer(trainer.get());

        return trainerRatingRepository.save(trainerRating);
    }

    public Page<TrainerRating> getRatings(Pageable pageable, int id) {
        return trainerRatingRepository.findAllByTrainerId(pageable, id);
    }
}
