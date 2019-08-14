package com.future.onlinetraining.repository;

import com.future.onlinetraining.entity.Classroom;
import com.future.onlinetraining.entity.ClassroomResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClassroomResultRepository extends JpaRepository<ClassroomResult, Integer> {
    Optional<ClassroomResult> findByUserIdAndClassroomId(int userId, int classroomId);

    List<ClassroomResult> findAllByClassroomId(int userId);

    @Query(
            value = "from ClassroomResult crs " +
            "inner join crs.classroom c " +
            "inner join crs.user u " +
            "inner join c.module m " +
            "where u.id = :userId " +
            "and crs.status = 'finished' " +
            "and crs.score >= c.minScore " +
            "or m.hasExam = false "
    )
    Page<ClassroomResult> getPassed(
            Pageable pageable, @Param("userId") int userId);

    @Query(
            value = "from ClassroomResult crs " +
                    "inner join crs.classroom c " +
                    "inner join crs.user u " +
                    "where u.id = :userId " +
                    "and crs.status = 'finished' " +
                    "and crs.score < c.minScore "
    )
    Page<ClassroomResult> getNotPassed(
            Pageable pageable, @Param("userId") int userId);
}
