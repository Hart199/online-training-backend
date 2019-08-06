package com.future.onlinetraining.repository;

import com.future.onlinetraining.entity.projection.TrainerData;
import com.future.onlinetraining.users.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TrainerRepository extends JpaRepository<User, Integer> {

    @Query (
            nativeQuery = true,
            value = "select u.id, u.fullname as name, u.photo, avg(tr.value) as rating " +
                    "from users u " +
                    "inner join trainer_ratings tr " +
                    "on u.id = tr.trainer_id " +
                    "inner join roles r " +
                    "on r.id = u.role_id " +
                    "where r.value = 'TRAINER' " +
                    "group by u.id " +
                    "order by rating desc"
    )
    Page<TrainerData> getTopTrainers(Pageable pageable);
}
