package com.example.tasktrackerb7.db.service.serviceimpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class S3Service {

    private final S3Client s3;

    @Value("${aws.path}")
    private String bucketPath;
    @Value("${aws.bucket.name}")
    private String bucketName;

    @Autowired
    public S3Service(S3Client s3) {
        this.s3 = s3;
    }


    public Map<String, String> upload(MultipartFile file) throws IOException {
        try {
            log.info("Uploading file...");
            String key = System.currentTimeMillis() + file.getOriginalFilename();
            PutObjectRequest por = PutObjectRequest
                    .builder()
                    .bucket(bucketName)
                    .contentType("jpeg")
                    .contentType("png")
                    .contentType("pdf")
                    .contentType("jfif")
                    .contentType("mp4")
                    .contentLength(file.getSize())
                    .key(key)
                    .build();

            s3.putObject(por, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            log.info("Upload complete");
            return Map.of(
                    "link", bucketPath + key
            );
        }catch (IOException e){
            log.error("IOException");
          throw new IOException();
        }
    }

    public Map<String, String> delete(String fileLink) {
        try {

            String key = fileLink.substring(bucketPath.length());

            log.warn("deleting object:  {}" + key);

            s3.deleteObject(dor -> dor.bucket(bucketName).key(key).build());

        } catch (S3Exception e) {
            throw new IllegalStateException(e.awsErrorDetails().errorMessage());
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }

        return Map.of(
                "message", fileLink + " has been deleted"
        );
    }
}
