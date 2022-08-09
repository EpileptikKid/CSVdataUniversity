package com.example.datauniversity.service;

import com.example.datauniversity.model.University;
import com.example.datauniversity.repository.CSVHelper;

import com.example.datauniversity.repository.UniversityRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class CSVService {
    final
    UniversityRepository repository;

    public CSVService(UniversityRepository repository) {
        this.repository = repository;
    }


    public void save(MultipartFile file) {
        try {
            List<University> universities = CSVHelper.csvToUniversities(file.getInputStream());
            repository.saveAll(universities);
        } catch (IOException e) {
            throw new RuntimeException("fail to store  csv data: " + e.getMessage());
        }
    }

    public ByteArrayInputStream load() {
        List<University> universities = repository.findAll();
        return CSVHelper.universitiesToCSV(universities);
    }

    public List<University> getAllUniversities() {
        return repository.findAll();
    }
}
