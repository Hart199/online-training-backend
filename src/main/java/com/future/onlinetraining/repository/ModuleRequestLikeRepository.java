package com.future.onlinetraining.repository;

import com.future.onlinetraining.entity.ModuleRequestLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ModuleRequestLikeRepository extends JpaRepository<ModuleRequestLike, Integer> {

    @Query(
            value = "from ModuleRequestLike mrl where mrl.moduleRequest.id = :id and mrl.user.id = :userId"
    )
    ModuleRequestLike findByIdAndUserId(@Param("id") int id, @Param("userId") int userId);
}
