package com.payoman.campaign.util;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Executors;

@PropertySource("classpath:AwsCredentials.properties")
@Service
@Slf4j
public class AwsUtils {

    public static String TYPE = "text/csv";

    @Value("${accessKey}")
    private String accessKey;

    @Value("${secretKey}")
    private String secretKey;

    private Regions clientRegion = Regions.AP_SOUTH_1;

    public String convertFileAndUpload(MultipartFile multipartFile, String bucketName) {
        log.info("Aws utils 1st stage ....");
        File file = convertMultipart(multipartFile);
        URL upload = upload(file, bucketName);
        return upload.toString();
    }

    public URL upload(File file, String bucketName) {
        log.info("Uploading file into AWS buckets " + bucketName);
        URL url = null;

        AmazonS3 amazonS3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .withRegion(clientRegion)
                .build();

        int maxUploadThreads = 5;

        TransferManager tm = TransferManagerBuilder
                .standard()
                .withS3Client(amazonS3)
                .withMultipartUploadThreshold((long) (5 * 1024 * 1024))
                .withExecutorFactory(() -> Executors.newFixedThreadPool(maxUploadThreads))
                .build();

        ProgressListener progressListener =
                progressEvent -> log.info("Transferred bytes: " + progressEvent.getBytesTransferred());

        PutObjectRequest request = new PutObjectRequest(bucketName, file.getName(), file);

        request.setGeneralProgressListener(progressListener);

        Upload upload = tm.upload(request);
        try {
            upload.waitForCompletion();
            log.info("Upload Completed.");
            url = amazonS3.getUrl(bucketName, file.getName());
            log.info("URL: " + url);
        } catch (Exception e) {
            log.error("Error occurred while uploading file. " + e.getMessage());
            e.getMessage();
        }
        return url;
    }


    public File convertMultipart(MultipartFile file) {
        log.info("Converting multipart file to normal file ...");
        File invoiceFile = new File("files/" + file.getOriginalFilename());
        // File invoiceFile = new File(file.getOriginalFilename());
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(invoiceFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {
            e.getMessage();
        }
        return invoiceFile;
    }
}
