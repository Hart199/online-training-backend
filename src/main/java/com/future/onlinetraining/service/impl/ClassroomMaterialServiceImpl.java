package com.future.onlinetraining.service.impl;

import com.future.onlinetraining.entity.Classroom;
import com.future.onlinetraining.entity.ClassroomMaterial;
import com.future.onlinetraining.entity.ClassroomSession;
import com.future.onlinetraining.repository.ClassroomMaterialRepository;
import com.future.onlinetraining.repository.ClassroomRepository;
import com.future.onlinetraining.service.ClassroomMaterialService;
import com.future.onlinetraining.service.FileHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.security.MessageDigest;
import java.util.Optional;

@Service("classroomMaterialService")
public class ClassroomMaterialServiceImpl implements ClassroomMaterialService {

    @Autowired
    ClassroomRepository classroomRepository;
    @Autowired
    ClassroomMaterialRepository classroomMaterialRepository;
    @Autowired
    FileHandlerService fileHandlerService;

    public ClassroomMaterial add(int classroomId, MultipartFile multipartFile) {
        Optional<Classroom> classroomOptional = classroomRepository.findById(classroomId);
        if (!classroomOptional.isPresent())
            throw new NullPointerException("Kelas tidak ditemukan.");

        ClassroomMaterial classroomMaterial = ClassroomMaterial
                .builder()
                .classroom(classroomOptional.get())
                .build();

        String hashedFilename = DigestUtils.md5DigestAsHex(
                (classroomOptional.get().getName() + "#" + classroomOptional.get().getId()).getBytes());

        String file = fileHandlerService.store(
                hashedFilename + "_" + multipartFile.getOriginalFilename(), multipartFile);
        if (file == null)
            throw new NullPointerException("Gagal mengupload file");

        classroomMaterial.setFile(file);
        return classroomMaterialRepository.save(classroomMaterial);
    }

    public ClassroomMaterial update(
            int classroomId, int materialId, MultipartFile multipartFile) {

        Optional<ClassroomMaterial> classroomMaterialOptional = classroomMaterialRepository
                .findByIdAndClassroomId(materialId, classroomId);
        if (!classroomMaterialOptional.isPresent())
            throw new NullPointerException("Materi kelas tidak ditemukan.");

        Classroom classroom = classroomMaterialOptional.get().getClassroom();
        String hashedFilename = DigestUtils.md5DigestAsHex(
                (classroom.getName() + "#" + classroom.getId()).getBytes());

        String file = fileHandlerService.update(
                classroomMaterialOptional.get().getFile(),
                hashedFilename + "_" + multipartFile.getOriginalFilename(), multipartFile);
        if (file == null)
            throw new NullPointerException("Gagal mengupload file");

        ClassroomMaterial classroomMaterial = classroomMaterialOptional.get();
        classroomMaterial.setFile(file);

        return classroomMaterialRepository.save(classroomMaterial);
    }

    public boolean delete(int classroomId, int materialId) {
        Optional<ClassroomMaterial> classroomMaterialOptional = classroomMaterialRepository
                .findByIdAndClassroomId(materialId, classroomId);
        if (!classroomMaterialOptional.isPresent())
            throw new NullPointerException("Materi kelas tidak ditemukan.");

        boolean success = fileHandlerService.delete(classroomMaterialOptional.get().getFile());
        if (success) {
            classroomMaterialRepository.deleteById(materialId);
            return true;
        }
        return false;
    }
}
