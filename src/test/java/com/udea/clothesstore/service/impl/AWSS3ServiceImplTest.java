package com.udea.clothesstore.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AWSS3ServiceImplTest {

    @Mock
    private AmazonS3 amazonS3;
    private AWSS3ServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new AWSS3ServiceImpl(amazonS3);
    }

    @Test
    void getObjectUrl() {
        String key = "kitten.png";
        String url = "https://null.s3.amazonaws.com/kitten.png";
        String keyExpected = underTest.getObjectUrl(key);
        assertThat(keyExpected).isEqualTo(url);
    }

    @Test
    void isNotImage() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "content.zip",
                "application/zip",
                new byte[10]
        );
        boolean expected = underTest.isNotImage(file);
        assertThat(expected).isTrue();
    }

    @Test
    void isImage() {
        MockMultipartFile file = new MockMultipartFile(
                "images",
                "kitten.png",
                "image/png",
                new byte[10]
        );
        boolean expected = underTest.isNotImage(file);
        assertThat(expected).isFalse();
    }

}