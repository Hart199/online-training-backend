package com.future.onlinetraining.repository;

import com.future.onlinetraining.entity.Classroom;
import com.future.onlinetraining.entity.ClassroomMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClassroomMaterialRepository extends JpaRepository<ClassroomMaterial, Integer> {

    @Query(value = "from ClassroomMaterial cm where cm.id = :id")
    ClassroomMaterial find(@Param("id") int id);

}
