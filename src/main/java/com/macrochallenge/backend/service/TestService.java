package com.macrochallenge.backend.service;

import com.macrochallenge.backend.exceptions.NotFoundException;
import com.macrochallenge.backend.model.*;
import com.macrochallenge.backend.model.dto.TestDTO;
import com.macrochallenge.backend.model.dto.TestResultDTO;
import com.macrochallenge.backend.repositories.SchoolRepository;
import com.macrochallenge.backend.repositories.TestRepository;
import com.macrochallenge.backend.repositories.UserRepository;
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
    private final UserRepository userRepository;

    @Autowired
    public TestService(TestRepository testRepository, SchoolRepository schoolRepository, UserRepository userRepository) {
        this.testRepository = testRepository;
        this.schoolRepository = schoolRepository;
        this.userRepository = userRepository;
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


    public TestResultDTO getTestResult(TestDTO testDTO, String userId) {
        Optional<SystemUser> userOptional = userRepository.findById(userId);

        if (!userOptional.isPresent()) {
            throw new NotFoundException("Usuário não encontrado");
        }

        SystemUser userForResult = userOptional.get();

       List<Results> resultsList = userForResult.getResults().stream()
               .filter(results -> (results.getTest().getName().equals(testDTO.getTestName())
                       && (results.getTest().getYear().equals(testDTO.getTestYear()))))
               .collect(Collectors.toList());

       if(resultsList.isEmpty()) {
           throw new NotFoundException("Resultado não encontrado");
       }

        Results lastResult = resultsList.get(resultsList.size() - 1);

        Test test = lastResult.getTest();

        if (test.getQuestions().isEmpty()) {
            throw new NotFoundException("Questões não encontradas");
        }

        List<Question> testQuestions = test.getQuestions();

        String answeredQuestions = lastResult.getAnsweredQuestions();

        return new TestResultDTO(testQuestions,answeredQuestions);
    }

    public List<TestDTO> getTestDTOs() {
        return  schoolRepository.findAll().stream()
                .flatMap(school -> school.getTests().stream())
                .map(test -> new TestDTO(test.getName(), test.getYear()))
                .collect(Collectors.toList());
    }
}
