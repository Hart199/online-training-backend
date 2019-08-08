package com.future.onlinetraining.repository;

import com.future.onlinetraining.entity.ClassroomResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassroomResultRepository extends JpaRepository<ClassroomResult, Integer> {
    Optional<ClassroomResult> findByUserIdAndClassroomId(int userId, int classroomId);
}
