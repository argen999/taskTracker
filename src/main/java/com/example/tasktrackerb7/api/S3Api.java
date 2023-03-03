package com.example.tasktrackerb7.api;

import com.example.tasktrackerb7.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/file")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "S3 api", description = "Upload files")
public class S3Api {
private final S3Service s3Service;

    @Operation(summary = "Upload file", description = "Upload file to database")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> uploadFile(@RequestParam(name = "file",required = false)  MultipartFile file) throws IOException {
        return s3Service.upload(file);
    }
    @Operation(summary = "Delete file", description = "Delete file from database")
    @DeleteMapping
    public Map<String, String> deleteFile(@RequestParam String fileLink) {
        return s3Service.delete(fileLink);
    }
}
