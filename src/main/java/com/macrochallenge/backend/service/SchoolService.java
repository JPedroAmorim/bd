package com.macrochallenge.backend.service;

import com.macrochallenge.backend.exceptions.NotFoundException;
import com.macrochallenge.backend.model.Results;
import com.macrochallenge.backend.model.dto.SchoolsAndTestHeadersDTO;
import com.macrochallenge.backend.model.dto.TestDTO;
import com.macrochallenge.backend.repositories.ResultsRepository;
import com.macrochallenge.backend.repositories.SchoolRepository;
import com.macrochallenge.backend.service.interfaces.SchoolServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class SchoolService implements SchoolServiceInterface {

    private final SchoolRepository schoolRepository;
    private final ResultsRepository resultRepository;

    @Autowired
    public SchoolService(SchoolRepository schoolRepository, ResultsRepository resultRepository) {
        this.schoolRepository = schoolRepository;
        this.resultRepository = resultRepository;
    }
// List<TestDTO> testDTOsForSchools = school.getTests().stream().map(test -> new TestDTO(test.getName(), test.getYear())).collect(Collectors.toList());
    @Override
    public List<SchoolsAndTestHeadersDTO> getSchools() {
        List<SchoolsAndTestHeadersDTO> schoolsAndTestHeadersDTOS = schoolRepository.findAll().stream().map(school -> {

            List<TestDTO> testDTOsForSchool = school.getTests().stream()
                    .map(test -> {
                        if(test.getResults().isEmpty()) {
                            return new TestDTO(test.getName(), test.getYear());
                        }

                        Results lastResult = test.getResults().get(test.getResults().size() - 1);

                        TestDTO testDTO = new TestDTO(test.getName(), test.getYear());

                        testDTO.setTotalNumberOfQuestionsForLastResult(String.valueOf(lastResult.getTotalNumberOfQuestions()));
                        testDTO.setNumberOfCorrectAnswersForLastResult(String.valueOf(lastResult.getTotalNumberOfCorrectAnswers()));

                        return testDTO;
                    })
                    .collect(Collectors.toList());




            return new SchoolsAndTestHeadersDTO(school.getName(), school.getLocation(), school.getSchoolLogoUrl(),
                    testDTOsForSchool);
        }).collect(Collectors.toList());

        if (schoolsAndTestHeadersDTOS.isEmpty()) {
            throw new NotFoundException("Não foram encontradas escolas");
        }

        return  schoolsAndTestHeadersDTOS;
    }

}
