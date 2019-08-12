package com.future.onlinetraining.controller;

import com.future.onlinetraining.dto.ClassroomDTO;
import com.future.onlinetraining.dto.ClassroomDetailDTO;
import com.future.onlinetraining.dto.ModuleClassroomDTO;
import com.future.onlinetraining.entity.Classroom;
import com.future.onlinetraining.entity.ClassroomResult;
import com.future.onlinetraining.entity.Module;
import com.future.onlinetraining.entity.projection.ClassroomDetailData;
import com.future.onlinetraining.service.ClassroomService;
import com.future.onlinetraining.utility.ResponseHelper;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.hibernate.Criteria;
import org.hibernate.NullPrecedence;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ClassroomController<T> {

    @Autowired
    ClassroomService classroomService;

    /**
     * Get all subscribed classrooms
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/classrooms/_subscribed")
    public ResponseEntity getSubscribed(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "5") int size,
                                        @RequestParam(value = "status", required = false) String status,
                                        @RequestParam(value = "passed", required = false) Boolean passed) {
        return new ResponseHelper<>()
                .setHttpStatus(HttpStatus.OK)
                .setParam("data", classroomService.getAllSubscribed(page, size, status, passed))
                .setSuccessStatus(true)
                .send();
    }

    /**
     * Get all classrooms
     * @param page
     * @param size
     * @param name
     * @param popular
     * @param hasExam
     * @return
     */
    @GetMapping("/classrooms")
    public ResponseEntity getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "popular", defaultValue = "false") boolean popular,
            @RequestParam(value = "hasExam", required = false) Boolean hasExam) {

        Pageable pageable;

        if (!popular)
            pageable = PageRequest.of(page, size);
        else {
            pageable = PageRequest.of(page, size, JpaSort.unsafe(
                    Sort.Direction.DESC, "case when avg(mrg.value) is null then 0.0 else avg(mrg.value) end"));
        }

        return new ResponseHelper<>()
                .setHttpStatus(HttpStatus.OK)
                .setParam("data", classroomService.all(name, hasExam, pageable))
                .setSuccessStatus(true)
                .send();
    }

    /**
     * Join classroom
     * @param id
     * @return
     */
    @PostMapping("/classrooms/{id}/_join")
    public ResponseEntity joinClassroom(@PathVariable("id") int id) {
        T object = (T) classroomService.join(id);

        if (object instanceof ClassroomResult)
            return new ResponseHelper<>()
                    .setParam("data", object)
                    .setMessage("Berhasil mengikuti kelas.")
                    .send();

        return new ResponseHelper<>()
                .setParam("data", object)
                .setMessage("Melakukan request kelas karena kelas tidak dapat mengikuti kelas")
                .send();
    }

    /**
     * Create a classroom
     * @param classroomDTO
     * @return
     */
    @PostMapping("/_trainer/classrooms")
    public ResponseEntity create(@RequestBody ClassroomDTO classroomDTO) {
        return new ResponseHelper<>()
                .setParam("data", classroomService.create(classroomDTO))
                .send();
    }

    /**
     * Get trainer classrooms
     * @param page
     * @param size
     * @param status
     * @return
     */
    @GetMapping("/_trainer/classrooms")
    public ResponseEntity getTrainerClassrooms(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "status", required = false) String status) {
        return new ResponseHelper<>()
                .setParam("data", classroomService.getTrainerClassrooms(PageRequest.of(page, size), status))
                .send();
    }

    /**
     * Create a module with classroom
     * @param moduleClassroomDTO
     * @return
     */
    @PostMapping("/_trainer/_modulesclassrooms")
    public ResponseEntity createModuleWithClassroom(@RequestBody ModuleClassroomDTO moduleClassroomDTO) {
        Classroom newClassroom = classroomService.createModuleAndClassroom(moduleClassroomDTO);

        if (newClassroom == null)
            return new ResponseHelper<>()
                    .setSuccessStatus(false)
                    .setMessage("Gagal membuat modul dan kelas")
                    .send();

        return new ResponseHelper<>()
                .setParam("data", newClassroom)
                .send();
    }

    /**
     * Get classroom detail
     * @param id
     * @return
     */
    @GetMapping("/classrooms/{id}")
    public ResponseEntity getClassroomDetail(@PathVariable("id") Integer id) {
        ClassroomDetailData classroom = classroomService.getClassroomDetail(id);

        if (classroom == null)
            return new ResponseHelper<>()
                    .setSuccessStatus(false)
                    .setMessage("Kelas tidak ditemukan")
                    .send();

        return new ResponseHelper<>()
                .setParam("data", classroom)
                .send();
    }

    /**
     * Edit classroom detail
     * @param id
     * @param classroomDTO
     * @return
     */
    @PutMapping(value= "/_trainer/classrooms/{id}")
    public ResponseEntity editDetail(
            @PathVariable("id") Integer id, @RequestBody ClassroomDetailDTO classroomDTO) {
        Classroom classroom = classroomService.editDetail(id, classroomDTO);

        if (classroom == null)
            return new ResponseHelper<>()
                    .setSuccessStatus(false)
                    .setMessage("Gagal mengedit kelas")
                    .send();

        return new ResponseHelper<>()
                .setParam("data", classroom)
                .send();
    }

    /**
     * Delete a classroom
     * @param id
     * @return
     */
    @DeleteMapping("/_trainer/classrooms/{id}")
    public ResponseEntity delete(@PathVariable("id") int id) {
        Boolean result = classroomService.delete(id);
        if (!result)
            return new ResponseHelper<>()
                    .setSuccessStatus(false)
                    .setMessage("Kelas tidak ditemukan")
                    .send();

        return new ResponseHelper<>()
                .setMessage("Berhasil menghapus kelas")
                .send();
    }

    /**
     * Delete classroom material
     * @param id
     * @return
     */
    @DeleteMapping("/_trainer/classrooms/_materials/{id}")
    public ResponseEntity deleteMaterial(@PathVariable("id") int id) {
        Boolean result = classroomService.deleteMaterial(id);
        if (!result)
            return new ResponseHelper<>()
                    .setSuccessStatus(false)
                    .setMessage("Materi kelas tidak ditemukan")
                    .send();

        return new ResponseHelper<>()
                .setMessage("Berhasil menghapus materi kelas")
                .send();
    }
}
