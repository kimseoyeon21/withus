package com.example.WITHUS.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UploadController {

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("sender") String sender,
                                                   @RequestParam("croomIdx") String croomIdx) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("파일이 비어있습니다.");
        }

        try {
            String uploadDir = "C:/withus_uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String filename = System.currentTimeMillis() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
            file.transferTo(new File(uploadDir + filename));

            return ResponseEntity.ok("파일 업로드 성공: " + filename);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("파일 저장 실패: " + e.getMessage());
        }
    }
}