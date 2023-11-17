package com.example.demo.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3Config {
    @Value("${aws.endpointURL:}")
    private String endpointUrl;
    @Value("${aws.signingRegion}")
    private String signingRegion;
    @Bean("demoAWSS3Bean")
    public AmazonS3 amazonS3(){
        AmazonS3 s3 = null;
        try{
            checkEnvironmentVariable();
            AmazonS3ClientBuilder s3ClientBuilder = AmazonS3ClientBuilder.standard().withPathStyleAccessEnabled(Boolean.TRUE);
            if(endpointUrl != null && signingRegion != null){
                s3ClientBuilder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpointUrl, signingRegion));
            }
            s3 = s3ClientBuilder.build();
        } catch(Exception e){
            System.out.println("Error in S3 Builder");
        }
        return s3;
    }

    private void checkEnvironmentVariable(){
        String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
        String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");

        if (accessKey == null || secretKey == null) {
            System.out.println("AWS credentials are not properly set.");
        } else {
            System.out.println("AWS credentials are set:");
            System.out.println("Access Key: " + accessKey);
            System.out.println("Secret Key: " + secretKey);
        }
    }
}
