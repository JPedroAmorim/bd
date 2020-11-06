package com.macrochallenge.backend.controllers;

import com.macrochallenge.backend.model.School;
import com.macrochallenge.backend.model.dto.SchoolsAndTestHeadersDTO;
import com.macrochallenge.backend.repositories.SchoolRepository;
import com.macrochallenge.backend.service.interfaces.SchoolServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/schools")
@RestController
public class SchoolController {

    private final SchoolServiceInterface schoolService;

    @Autowired
    public SchoolController(SchoolServiceInterface schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping
    public List<SchoolsAndTestHeadersDTO> getSchoolAndTestHeaders() {
        return schoolService.getSchools();
    }
}
