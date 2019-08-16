package com.future.onlinetraining.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileHandlerService {

    String store (String filename, MultipartFile multipartFile);
    String update(String oldFilename, String newFilename, MultipartFile multipartFile);
    boolean delete(String filename);
}
