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

    public String store (String filename, MultipartFile multipartFile) {
        File file = new File(uploadDir + filename);
        try {
            multipartFile.transferTo(file);
        } catch (Exception e) {
            return null;
        }

        return filename;
    }

    public String update(
            String oldFilename, String newFilename, MultipartFile multipartFile) {
        File file = new File(uploadDir + oldFilename);
        if (file.delete()) {
            file = new File(uploadDir + newFilename);
            try {
                multipartFile.transferTo(file);
                return newFilename;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public boolean delete(String filename) {
        File file = new File(uploadDir + filename);
        return file.delete();
    }
}
