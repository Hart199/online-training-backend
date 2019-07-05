package com.future.onlinetraining.repository;

import com.future.onlinetraining.entity.ClassroomRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRequestRepository extends JpaRepository<ClassroomRequest, Integer> {
}
