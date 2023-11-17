package com.example.demo.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;

@Service
public class S3FileManager {

    @Autowired
    @Qualifier("demoAWSS3Bean")
    private AmazonS3 s3;

    @Value("${aws.bucket}")
    private String bucketName;

    private static final String OBJECT_KEY = "test/demo2.pdf";

    public String uploadFileToS3(){
        try{
            File file = ResourceUtils.getFile("classpath:INVOICE#00000001.pdf");
            FileInputStream fis = new FileInputStream(file);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType("application/pdf");
            objectMetadata.setContentLength(file.length());
            PutObjectRequest putObj = new PutObjectRequest(bucketName, OBJECT_KEY, fis, objectMetadata);
            Region re =  s3.getRegion();
            System.out.println(re.toString());
            s3.putObject(putObj);
            return "Success";
        } catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
            return "Error";
        }
    }

    public String generatePresignedURL(){
        Date expDate = new Date();
        long expTimeMillis = expDate.getTime();
        expTimeMillis += 1000 * 60 * 60;
        expDate.setTime(expTimeMillis);
        URL url = s3.generatePresignedUrl(bucketName, OBJECT_KEY, expDate, HttpMethod.GET);
        return url.toString();
    }
}
