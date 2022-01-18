package com.udea.clothesstore.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface AWSS3Service {
    String uploadFile(MultipartFile file);

    String getObjectUrl(String key);
}
