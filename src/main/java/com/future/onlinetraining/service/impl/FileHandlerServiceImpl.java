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

    private String dirPath = "";
    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    void checkDir() {
        if (!dirPath.equals("")) {
            File dir = new File(uploadDir + dirPath);
            if (!dir.exists())
                dir.mkdirs();
        }
    }

    public String store (String filename, MultipartFile multipartFile) {
        checkDir();
        File file = new File(uploadDir + dirPath + filename);
        try {
            multipartFile.transferTo(file);
        } catch (Exception e) {
            return null;
        }

        return filename;
    }

    public String update(
            String oldFilename, String newFilename, MultipartFile multipartFile) {
        checkDir();
        File file = new File(uploadDir + dirPath + oldFilename);
        file.delete();
        file = new File(uploadDir + dirPath + newFilename);
        try {
            multipartFile.transferTo(file);
            return newFilename;
        } catch (Exception e) {
            return null;
        }

    }

    public boolean delete(String filename) {
        File file = new File(uploadDir + dirPath + filename);
        return file.delete();
    }
}
