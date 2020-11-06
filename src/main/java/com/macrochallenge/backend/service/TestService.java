package com.macrochallenge.backend.service;

import com.macrochallenge.backend.exceptions.NotFoundException;
import com.macrochallenge.backend.model.Question;
import com.macrochallenge.backend.model.Results;
import com.macrochallenge.backend.model.School;
import com.macrochallenge.backend.model.Test;
import com.macrochallenge.backend.model.dto.TestDTO;
import com.macrochallenge.backend.model.dto.TestResultDTO;
import com.macrochallenge.backend.repositories.SchoolRepository;
import com.macrochallenge.backend.repositories.TestRepository;
import com.macrochallenge.backend.service.interfaces.TestServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestService implements TestServiceInterface {

    private final TestRepository testRepository;
    private final SchoolRepository schoolRepository;

    @Autowired
    public TestService(TestRepository testRepository, SchoolRepository schoolRepository) {
        this.testRepository = testRepository;
        this.schoolRepository = schoolRepository;
    }

    @Override
    public List<Question> getTestQuestions(TestDTO testDTO) {
        Optional<Test> testOptional = testRepository.findByNameAndYear(testDTO.getTestName(), testDTO.getTestYear());

        if (!testOptional.isPresent()) {
            throw new NotFoundException("Prova não encontrada");
        }

        Test test = testOptional.get();

        if(test.getQuestions().isEmpty()) {
            throw new NotFoundException("Questões não encontradas");
        }

        return testOptional.get().getQuestions();
    }


    public TestResultDTO getTestResult(TestDTO testDTO) {
        Optional<Test> testOptional = testRepository.findByNameAndYear(testDTO.getTestName(), testDTO.getTestYear());

        if (!testOptional.isPresent()) {
            throw new NotFoundException("Prova não encontrada");
        }

        Test test = testOptional.get();

        if(test.getQuestions().isEmpty()) {
            throw new NotFoundException("Questões não encontradas");
        }

        List<Question> testQuestions = test.getQuestions();

        if(test.getResults().isEmpty()) {
            throw new NotFoundException("Resultado não encontrado");
        }

        Results lastResult = test.getResults().get(test.getResults().size() - 1);

        String answeredQuestions = lastResult.getAnsweredQuestions();

        TestResultDTO testResultDTO = new TestResultDTO(testQuestions,answeredQuestions);

        return testResultDTO;
    }

    public List<TestDTO> getTestDTOs() {
        return  schoolRepository.findAll().stream()
                .flatMap(school -> school.getTests().stream())
                .map(test -> new TestDTO(test.getName(), test.getYear()))
                .collect(Collectors.toList());
    }
}
