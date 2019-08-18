package com.future.onlinetraining.repository;

import com.future.onlinetraining.entity.Module;
import com.future.onlinetraining.entity.projection.GetAllModuleData;
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

//    @Query(
//            value = "select new com.future.onlinetraining.entity.projection.GetAllModuleData(" +
//                    "m.id, m.name, m.description, avg(mr.value) as rating, m.timePerSession, mc.name, " +
//                    "count(c), count(cs), m.hasExam, m.version) " +
//                    "from Module m left join m.moduleRatings mr inner join m.classrooms c " +
//                    "left join c.classroomSessions cs inner join m.moduleCategory mc " +
//                    "where (:nameParam is null or lower(m.name) like :nameParam%) " +
//                    "and (:categoryParam is null or mc.name = :categoryParam ) " +
//                    "and (:hasExam is null or m.hasExam = :hasExam) " +
//                    "group by m, mc, c"
//    )
    @Query(
            value = "from Module m left join m.moduleRatings mr inner join m.classrooms c " +
                    "left join c.classroomSessions cs inner join m.moduleCategory mc " +
                    "where (:nameParam is null or lower(m.name) like :nameParam%) " +
                    "and (:categoryParam is null or mc.name = :categoryParam ) " +
                    "and (:hasExam is null or m.hasExam = :hasExam) "
    )
    Page<Module> getAllBySearhTerm(
            Pageable pageable, @Param("nameParam") String name,
            @Param("categoryParam") String category, @Param("hasExam") Boolean hasExam);

    @Query(
            value = "select new com.future.onlinetraining.entity.projection.ModuleDetailData(m, avg(mr.value), m.hasExam) " +
                    "from Module m left join m.moduleRatings mr " +
                    "where m.id = :id group by m.id "
    )
    ModuleDetailData find(@Param("id") Integer id);

}
