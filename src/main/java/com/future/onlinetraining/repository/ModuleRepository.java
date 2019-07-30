package com.future.onlinetraining.repository;

import com.future.onlinetraining.entity.Module;
import com.future.onlinetraining.entity.projection.ModuleData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ModuleRepository extends JpaRepository<Module, Integer> {

    @Query (
            value = "select m from Module m")
    Page<Module> all(Pageable pageable);

//    @Query (
//            nativeQuery = true,
//            value = "select m.id, m.name as name, avg(mr.value) as rating, m.description as desc, " +
//                    "m.time_per_session as timePerSession, mc.name as category, " +
//                    "count(c.id) as classroomCount, count(ms.id) as sessionCount " +
//                    "from modules m " +
//                    "inner join module_ratings mr " +
//                    "on m.id = mr.module_id " +
//                    "inner join classrooms c " +
//                    "on m.id = c.module_id " +
//                    "inner join module_sessions ms " +
//                    "on m.id = ms.module_id " +
//                    "inner join module_categories mc " +
//                    "on mc.id = m.module_category_id " +
//                    "group by m.id, mc.id "
//    )
//    Page<ModuleData> getAllModule(Pageable pageable);

    @Query (
            nativeQuery = true,
            value = "select m.id, m.name as name, avg(mr.value) as rating, m.description as desc, " +
                    "m.time_per_session as timePerSession, mc.name as category, " +
                    "count(c.id) as classroomCount, count(ms.id) as sessionCount, " +
                    "(select count(c2.id) from classrooms c2 where c2.status = 'open' and c2.module_id = m.id) as openClassroomCount, " +
                    "(select count(c2.id) from classrooms c2 where c2.status = 'closed' and c2.module_id = m.id) as closedClassroomCount, " +
                    "(select case when count(ms.id) > 0 then true else false end " +
                    "from module_sessions ms2 where ms2.is_exam = true and ms2.module_id = m.id) as hasExam " +
                    "from modules m " +
                    "inner join module_ratings mr " +
                    "on m.id = mr.module_id " +
                    "inner join classrooms c " +
                    "on m.id = c.module_id " +
                    "inner join module_sessions ms " +
                    "on m.id = ms.module_id " +
                    "inner join module_categories mc " +
                    "on mc.id = m.module_category_id " +
                    "where (:nameParam is null or lower(m.name) like concat('%', :nameParam, '%')) " +
                    "and (:categoryParam is null or mc.name = concat(:categoryParam)) " +
                    "and (:hasExam is null or " +
                    "((select count(ms3.id) from module_sessions ms3 where ms3.module_id = m.id and ms3.is_exam = true) > 0 " +
                    "and concat(:hasExam) = 't') or " +
                    "((select count(ms3.id) from module_sessions ms3 where ms3.module_id = m.id and ms3.is_exam = true) = 0 " +
                    "and concat(:hasExam) = 'f')) " +
                    "group by m.id, mc.id"
    )
    Page<ModuleData> getAllBySearhTerm(
            Pageable pageable, @Param("nameParam") String name,
            @Param("categoryParam") String category, @Param("hasExam") Boolean hasExam);

}
