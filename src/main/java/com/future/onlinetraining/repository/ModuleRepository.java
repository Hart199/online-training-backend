package com.future.onlinetraining.repository;

import com.future.onlinetraining.entity.Module;
import com.future.onlinetraining.entity.projection.GetAllModuleData;
import com.future.onlinetraining.entity.projection.ModuleData;
import com.future.onlinetraining.entity.projection.ModuleDetailData;
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

//    @Query (
//            nativeQuery = true,
//            value = "select m.id, m.name as name, avg(mr.value) as rating, m.description as desc, " +
//                    "m.time_per_session as timePerSession, mc.name as category, " +
//                    "count(c.id) as classroomCount, count(cs.id) as sessionCount, " +
//                    "(select count(c2.id) from classrooms c2 where c2.status = 'open' and c2.module_id = m.id) as openClassroomCount, " +
//                    "(select count(c2.id) from classrooms c2 where c2.status = 'closed' and c2.module_id = m.id) as closedClassroomCount, " +
//                    "m.has_exam as hasExam, m.version as version " +
//                    "from modules m " +
//                    "inner join module_ratings mr " +
//                    "on m.id = mr.module_id " +
//                    "inner join classrooms c " +
//                    "on m.id = c.module_id " +
//                    "inner join classroom_sessions cs " +
//                    "on c.id = cs.classroom_id " +
//                    "inner join module_categories mc " +
//                    "on mc.id = m.module_category_id " +
//                    "where (:nameParam is null or lower(m.name) like concat('%', :nameParam, '%')) " +
//                    "and (:categoryParam is null or mc.name = concat(:categoryParam)) " +
//                    "and (:hasExam is null or " +
//                    "(m.has_exam = true and concat(:hasExam) = 't') or " +
//                    "(m.has_exam = false and concat(:hasExam) = 'f' )) " +
//                    "group by m.id, mc.id, c.id "
//    )
//    Page<ModuleData> getAllBySearhTerm(
//            Pageable pageable, @Param("nameParam") String name,
//            @Param("categoryParam") String category, @Param("hasExam") Boolean hasExam);

    @Query(
            value = "select new com.future.onlinetraining.entity.projection.GetAllModuleData(" +
                    "m.id, m.name, m.description, avg(mr.value) as rating, m.timePerSession, mc.name, " +
                    "count(c), count(cs), " +
                    "m.hasExam, m.version) " +
                    "from Module m " +
                    "left join m.moduleRatings mr " +
                    "inner join m.classrooms c " +
                    "inner join c.classroomSessions cs " +
                    "inner join m.moduleCategory mc " +
                    "where (:nameParam is null or lower(m.name) like :nameParam%) " +
                    "and (:categoryParam is null or mc.name = :categoryParam ) " +
                    "and (:hasExam is null or " +
                    "(m.hasExam = true and :hasExam = true) or " +
                    "(m.hasExam = false and :hasExam = false)) " +
                    "group by m, mc, c"
    )
    Page<GetAllModuleData> getAllBySearhTerm(
            Pageable pageable, @Param("nameParam") String name,
            @Param("categoryParam") String category, @Param("hasExam") Boolean hasExam);

    @Query(
            value = "select new com.future.onlinetraining.entity.projection.ModuleDetailData(m, avg(mr.value), m.hasExam) " +
                    "from Module m " +
                    "left join m.moduleRatings mr " +
                    "where m.id = :id " +
                    "group by m.id "
    )
    ModuleDetailData find(@Param("id") Integer id);

}
