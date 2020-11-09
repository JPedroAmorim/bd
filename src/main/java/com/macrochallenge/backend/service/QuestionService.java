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

        School schoolForQuestion = schoolForQuestionsOptional.get();

        String topicDecoded = "";

        if(topic.equals("cn")) {
            topicDecoded = "Ciências Naturais";
        } else if(topic.equals("m")) {
            topicDecoded = "Matemática";
        } else if(topic.equals("pt")) {
            topicDecoded = "Português";
        }

        String finalTopicDecoded = topicDecoded;

        List<Question> questionsForTopic =  schoolRepository.findAll().stream()
                .flatMap(school -> school.getTests().stream())
                .flatMap(test -> test.getQuestions().stream())
                .filter(question -> question.getTopic().equals(finalTopicDecoded))
                .collect(Collectors.toList());

        return questionsForTopic;
    }
}
