package com.future.onlinetraining.repository;

import com.future.onlinetraining.entity.ModuleRequest;
import com.future.onlinetraining.entity.projection.ModuleRequestData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ModuleRequestRepository extends JpaRepository<ModuleRequest, Integer> {

    Page<ModuleRequest> findAll(Pageable pageable);

    @Query(
            value = "select new com.future.onlinetraining.entity.projection.ModuleRequestData(mr) " +
                    "from ModuleRequest mr " +
                    "left join mr.moduleRequestLikes mrl " +
                    "where (:nameParam is null or lower(mr.title) like %:nameParam%) " +
                    "and mr.status = 'waiting' " +
                    "group by mr"
    )
    Page<ModuleRequestData> findAllByNameParam(Pageable pageable, @Param("nameParam") String name);

    @Query(
            value = "select new com.future.onlinetraining.entity.projection.ModuleRequestData(mr) " +
                    "from ModuleRequest mr " +
                    "left join mr.moduleRequestLikes mrl " +
                    "inner join mr.user u " +
                    "left join mrl.user u2 " +
                    "where (:nameParam is null or lower(mr.title) like %:nameParam%) " +
                    "and (:status is null or mr.status = :status) " +
                    "and (u2.id = :id or u.id = :id) " +
                    "group by mr"
    )
    Page<ModuleRequestData> findAllByUser(
            Pageable pageable, @Param("id") int id, @Param("nameParam") String name, @Param("status") String status);
}
