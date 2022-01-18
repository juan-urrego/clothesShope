package com.udea.clothesstore.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.udea.clothesstore.exception.ApiRequestException;
import com.udea.clothesstore.service.AWSS3Service;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class AWSS3ServiceImpl implements AWSS3Service {

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public AWSS3ServiceImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        if (isNotImage(multipartFile))
            throw new ApiRequestException("file not allowed");
        if (isInvalidSize(multipartFile))
            throw new ApiRequestException("file size not valid");
        File mainFile = new File(multipartFile.getOriginalFilename());
        String key = System.currentTimeMillis() + "_" + mainFile.getName();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        try {
            PutObjectRequest request = new PutObjectRequest(bucketName, key, multipartFile.getInputStream(), objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(request);
            return key;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String getObjectUrl(String key) {
        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, key);
    }

    public boolean isNotImage(MultipartFile multipartFile)  {
        String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        return !extension.equals("jpeg") && !extension.equals("jpg") && !extension.equals("png");
    }

    public boolean isInvalidSize(MultipartFile multipartFile) {
        if (multipartFile.getSize() > 1048576)
            return true;
        return false;
    }


}
