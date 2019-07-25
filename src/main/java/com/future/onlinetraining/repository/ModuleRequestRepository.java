package com.future.onlinetraining.repository;

import com.future.onlinetraining.entity.ModuleRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ModuleRequestRepository extends JpaRepository<ModuleRequest, Integer> {

    Page<ModuleRequest> findAll(Pageable pageable);

    @Query(
            value = "select mr from ModuleRequest mr " +
                    "where (:nameParam is null or lower(mr.title) like %:nameParam%) "
    )
    Page<ModuleRequest> findAllByNameParam(Pageable pageable, @Param("nameParam") String name);
}
