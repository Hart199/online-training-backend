package com.future.onlinetraining.repository;

import com.future.onlinetraining.entity.Module;
import com.future.onlinetraining.entity.projection.ModuleData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ModuleRepository extends JpaRepository<Module, Integer> {

    @Query (
            value = "select m from Module m")
    Page<Module> all(Pageable pageable);

    @Query (
            nativeQuery = true,
            value = "select m.id, m.name as name, avg(mr.value) as rating, m.description as desc, " +
                    "m.time_per_session as timePerSession, mc.name as category, " +
                    "count(c.id) as classroomCount, count(ms.id) as sessionCount " +
                    "from modules m " +
                    "inner join module_ratings mr " +
                    "on m.id = mr.module_id " +
                    "inner join classrooms c " +
                    "on m.id = c.module_id " +
                    "inner join module_sessions ms " +
                    "on m.id = ms.module_id " +
                    "inner join module_categories mc " +
                    "on mc.id = m.module_category_id " +
                    "group by m.id, mc.id "
    )
    Page<ModuleData> getAllModule(Pageable pageable);

}
