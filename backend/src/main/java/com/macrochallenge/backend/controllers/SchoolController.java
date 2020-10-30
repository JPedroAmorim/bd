package com.macrochallenge.backend.controllers;

import com.macrochallenge.backend.model.School;
import com.macrochallenge.backend.repositories.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/schools")
@RestController
public class SchoolController {

    private final SchoolRepository schoolRepository;

    @Autowired
    public SchoolController(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @PostMapping("/school-submission")
    public ResponseEntity<String> addNewSchool(@RequestBody School school) {

        schoolRepository.save(school);
        schoolRepository.flush();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
