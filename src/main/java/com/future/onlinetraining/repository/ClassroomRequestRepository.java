package com.future.onlinetraining.repository;

import com.future.onlinetraining.entity.ClassroomRequest;
import com.future.onlinetraining.entity.projection.ClassroomRequestsData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClassroomRequestRepository extends JpaRepository<ClassroomRequest, Integer> {

    @Query(
            value = "SELECT new com.future.onlinetraining.entity.projection.ClassroomRequestsData(c.name, " +
                    "c.id, t.fullname, " +
                    "count(c.id)) " +
                    "FROM ClassroomRequest cr " +
                    "inner join cr.classroom as c " +
                    "inner join c.trainer as t " +
                    "group by c, t"
    )
    Page<ClassroomRequestsData> getAll(Pageable pageable);

    @Query(
            value = "from ClassroomRequest cr where cr.classroom.id = :classroomId and cr.user.id = :userId"
    )
    ClassroomRequest findByClassroomIdandUserId(
            @Param("classroomId") int classroomId, @Param("userId") int userId);
}
