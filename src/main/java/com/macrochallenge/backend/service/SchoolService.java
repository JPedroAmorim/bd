package com.macrochallenge.backend.service;

import com.macrochallenge.backend.exceptions.NotFoundException;
import com.macrochallenge.backend.model.dto.SchoolsAndTestHeadersDTO;
import com.macrochallenge.backend.model.dto.TestDTO;
import com.macrochallenge.backend.repositories.SchoolRepository;
import com.macrochallenge.backend.service.interfaces.SchoolServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class SchoolService implements SchoolServiceInterface {

    private final SchoolRepository schoolRepository;

    @Autowired
    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }
// List<TestDTO> testDTOsForSchools = school.getTests().stream().map(test -> new TestDTO(test.getName(), test.getYear())).collect(Collectors.toList());
    @Override
    public List<SchoolsAndTestHeadersDTO> getSchools() {
        List<SchoolsAndTestHeadersDTO> schoolsAndTestHeadersDTOS = schoolRepository.findAll().stream().map(school -> {
            List<TestDTO> testDTOsForSchool = school.getTests().stream()
                    .map(test -> new TestDTO(test.getName(), test.getYear()))
                    .collect(Collectors.toList());
            return new SchoolsAndTestHeadersDTO(school.getName(), school.getLocation(), school.getSchoolLogoUrl(), testDTOsForSchool);
        }).collect(Collectors.toList());

        if (schoolsAndTestHeadersDTOS.isEmpty()) {
            throw new NotFoundException("Não foram encontradas escolas");
        }

        return  schoolsAndTestHeadersDTOS;
    }
}
