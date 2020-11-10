package com.macrochallenge.backend.service;

import com.macrochallenge.backend.exceptions.NotFoundException;
import com.macrochallenge.backend.model.Question;
import com.macrochallenge.backend.model.School;
import com.macrochallenge.backend.repositories.SchoolRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final SchoolRepository schoolRepository;

    public QuestionService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public List<Question> getQuestionsForTopicAndSchool(String schoolName, String topic) {
        Optional<School> schoolForQuestionsOptional = schoolRepository.findByName(schoolName);

        if (!schoolForQuestionsOptional.isPresent()) {
            throw new NotFoundException("Escola não encontrada");
        }

        String topicDecoded = "";

        switch (topic) {
            case "cn":
                topicDecoded = "Ciências Naturais";
                break;
            case "m":
                topicDecoded = "Matemática";
                break;
            case "pt":
                topicDecoded = "Português";
                break;
        }

        String finalTopicDecoded = topicDecoded;

        List<Question> questionsForTopic =  schoolRepository.findAll().stream()
                .flatMap(school -> school.getTests().stream())
                .flatMap(test -> test.getQuestions().stream())
                .filter(question -> question.getTopic().equals(finalTopicDecoded))
                .collect(Collectors.toList());

        if (questionsForTopic.isEmpty()) {
            throw new NotFoundException("Questões não encontradas");
        }

        return questionsForTopic;
    }
}
