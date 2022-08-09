package com.example.datauniversity.controller;

import com.example.datauniversity.ResponseMessage;
import com.example.datauniversity.model.University;
import com.example.datauniversity.repository.CSVHelper;
import com.example.datauniversity.service.CSVService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Objects;

@CrossOrigin("http://localhost:8080")
@Controller
@RequestMapping("/api/csv")
public class CSVController {
    final
    CSVService fileService;

    public CSVController(CSVService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file")MultipartFile file) {
        String message;

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                fileService.save(file);
                message = "Uploading the file successfully: " + file.getOriginalFilename();
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/csv/download/")
                        .path(Objects.requireNonNull(file.getOriginalFilename()))
                        .toUriString();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, fileDownloadUri));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message, ""));
            }
        }
        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message, ""));
    }

    @GetMapping("/universities")
    public ResponseEntity<List<University>> getAllUniversities() {
        try {
            List<University> universities = fileService.getAllUniversities();
            System.out.println(universities.size() + " - size upload data");
            if (universities.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(universities, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        InputStreamResource file = new InputStreamResource(fileService.load());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }
}
