package com.future.onlinetraining.service.impl;

import com.future.onlinetraining.service.FileHandlerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service("fileHandlerService")
public class FileHandlerServiceImpl implements FileHandlerService {

    @Value("${filePath}")
    private String uploadDir;

    public List<String> store (MultipartFile[] multipartFiles) {
        System.out.println(multipartFiles);
        List<String > uploadedFiles = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            File file = new File(uploadDir + multipartFile.getOriginalFilename());
            try {
                multipartFile.transferTo(file);
                uploadedFiles.add(multipartFile.getOriginalFilename());
            } catch (Exception e) {
                return null;
            }
        }
        return uploadedFiles;
    }
}
