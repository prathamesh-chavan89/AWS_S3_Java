package com.example.demo.controller;

import com.example.demo.service.S3FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller{

    @Autowired
    private S3FileManager s3FileManager;

    @GetMapping("/uploadFile")
    public String uploadObjectTest(){
        return s3FileManager.uploadFileToS3();
    }

    @GetMapping("/url")
    public String generateURL(){
        return s3FileManager.generatePresignedURL();
    }
}
