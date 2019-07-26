package com.future.onlinetraining.repository;

import com.future.onlinetraining.entity.ModuleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModuleCategoryRepository extends JpaRepository<ModuleCategory, Integer> {

    @Query(
            value = "select mc from ModuleCategory mc")
    List<ModuleCategory> all();

    @Query(
            value = "select mc from ModuleCategory mc where mc.name = :name"
    )
    ModuleCategory getByName(@Param("name") String name);

    ModuleCategory findByName(String name);
}
