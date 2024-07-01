package com.payoman.campaign.controller;

import com.payoman.campaign.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FileResource {

    @Autowired
    private FileUtil fileUtil;

    @PostMapping("/upload")
    private ResponseEntity<Object> readFile(@RequestPart(value = "document") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        try {
            fileUtil.parseExcel(file.getInputStream());
            result.put("status", "PROCESSING");
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (IOException e) {
            result.put("status", "FAILED");
            result.put("message", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/old-report-upload")
    private ResponseEntity<Object> processFile(@RequestPart(value = "document") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        try {
            fileUtil.parseOldReport(file.getInputStream());
            result.put("status", "PROCESSING");
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (IOException e) {
            result.put("status", "FAILED");
            result.put("message", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
