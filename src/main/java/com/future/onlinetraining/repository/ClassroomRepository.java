package com.future.onlinetraining.repository;

import com.future.onlinetraining.entity.Classroom;
import com.future.onlinetraining.entity.projection.ClassroomData;
import com.future.onlinetraining.entity.projection.ClassroomDetailData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {

    List<Classroom> findAll();

    Page<Classroom> findAll(Pageable pageable);

    @Query(
            value = "from Classroom c " +
                    "inner join c.classroomResults crs " +
                    "inner join crs.user u " +
                    "where u.id = :userId " +
                    "and (:status is null or crs.status = :status) "
    )
    Page<Classroom> findSubscribed(
            Pageable pageable, @Param("userId") int userId, @Param("status") String status);

    @Query(
            value = "select new com.future.onlinetraining.entity.projection.ClassroomData(" +
                    "c.id, c.name, m.name, t.fullname, c.status, c.min_member, c.max_member, " +
                    "(select count(cres) from cres), (select count(cr) from cr), " +
                    "case when avg(mrg.value) is null then 0.0 else avg(mrg.value) end, m.version ) " +
                    "from Classroom c inner join c.module m inner join m.moduleCategory mc " +
                    "inner join c.trainer t left join c.classroomRequests cr " +
                    "left join c.classroomResults cres left join m.moduleRatings mrg " +
                    "where (:nameParam is null or lower(m.name) like %:nameParam%) " +
                    "and (:hasExam is null or m.hasExam = :hasExam) " +
                    "group by c, m, t"
    )
    Page<ClassroomData> all(Pageable pageable, @Param("nameParam") String name, @Param("hasExam") Boolean hasExam);

    @Query(
            value = "from Classroom c where c.id = :id"
    )
    Classroom find(@Param("id") Integer id);

    @Query(
            value = "select new com.future.onlinetraining.entity.projection.ClassroomDetailData(c, count(crs)) " +
            "from Classroom c left join c.classroomResults crs " +
            "where c.id = :id group by c ")
    ClassroomDetailData getDetail(@Param("id") Integer id);

    @Query(
            value = "from Classroom c where c.trainer.id = :id " +
                    "and (:status is null or c.status = :status) "
    )
    Page<Classroom> getTrainerClassrooms(Pageable pageable, @Param("id") int id, @Param("status") String status);

    @Query(
            value = "from Classroom c " +
                    "inner join c.classroomRequests cr " +
                    "inner join cr.user u " +
                    "where u.id = :id " +
                    "and (:name is null or c.name like %:name%) " +
                    "and (:status is null or cr.status = :status) " +
                    "group by c, cr, u "
    )
    Page<Classroom> getRequestedUserClassroom(
            Pageable pageable, @Param("id") int id, @Param("name") String name, @Param("status") String status);
}
