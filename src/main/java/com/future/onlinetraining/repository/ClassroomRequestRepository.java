package com.future.onlinetraining.repository;

import com.future.onlinetraining.entity.ClassroomRequest;
import com.future.onlinetraining.entity.projection.ClassroomRequestsData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassroomRequestRepository extends JpaRepository<ClassroomRequest, Integer> {

    @Query(
            value = "SELECT new com.future.onlinetraining.entity.projection.ClassroomRequestsData(c.name, " +
                    "c.id, t.fullname, " +
                    "count(c.id) as requesterCount, m.name, min(cr.createdAt) as createdAt ) " +
                    "FROM ClassroomRequest cr " +
                    "inner join cr.classroom as c " +
                    "inner join c.trainer as t " +
                    "inner join c.module m " +
                    "where (:name is null or c.name like :name%) " +
                    "group by c, t, m"
    )
    Page<ClassroomRequestsData> getAll(Pageable pageable, @Param("name") String name);

    @Query(
            value = "SELECT new com.future.onlinetraining.entity.projection.ClassroomRequestsData(c.name, " +
                    "c.id, t.fullname, " +
                    "count(c.id) as requesterCount, m.name, min(cr.createdAt) as createdAt ) " +
                    "FROM ClassroomRequest cr " +
                    "inner join cr.classroom as c " +
                    "inner join c.trainer as t " +
                    "inner join c.module m " +
                    "where t.id = :id and " +
                    "(:name is null or c.name like :name%) " +
                    "group by c, t, m"
    )
    Page<ClassroomRequestsData> getAllbyTrainerId(Pageable pageable, @Param("id") int id, @Param("name") String name);

    @Query(
            value = "SELECT new com.future.onlinetraining.entity.projection.ClassroomRequestsData(c.name, " +
                    "c.id, t.fullname, " +
                    "count(c.id) as requesterCount, m.name, min(cr.createdAt) as createdAt ) " +
                    "FROM ClassroomRequest cr " +
                    "inner join cr.classroom as c " +
                    "inner join c.trainer as t " +
                    "inner join cr.user u " +
                    "inner join c.module m " +
                    "where u.id = :id and " +
                    "(:name is null or c.name like :name%) and " +
                    "(:status is null or cr.status = :status) " +
                    "group by c, t, m"
    )
    Page<ClassroomRequestsData> getAllbyUserId(
            Pageable pageable, @Param("id") int id, @Param("name") String name, @Param("status") String status);

    @Query(
            value = "from ClassroomRequest cr where cr.classroom.id = :classroomId and cr.user.id = :userId and cr.status = 'waiting' "
    )
    ClassroomRequest findByClassroomIdandUserId(
            @Param("classroomId") int classroomId, @Param("userId") int userId);

    List<ClassroomRequest> findAllByClassroomId(int id);

    void deleteAllByClassroomId(int classroomId);
}
