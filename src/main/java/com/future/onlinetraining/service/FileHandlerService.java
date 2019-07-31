package com.future.onlinetraining.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileHandlerService {

    List<String> store(MultipartFile[] files);
}
