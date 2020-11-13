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

                case "Português":
                    topicDecoded = "pt";
                    break;
                case "Matemática":
                    topicDecoded = "m";
                    break;
                case "Ciências Naturais":
                    topicDecoded = "cn";
                    break;
                case "Química":
                    topicDecoded = "q";
                    break;
                case "História":
                    topicDecoded = "h";
                    break;
                case "Biologia":
                    topicDecoded = "b";
                    break;
                case "Geografia":
                    topicDecoded = "g";
                    break;
                case "Física":
                    topicDecoded = "f";
                    break;
                default:
                    topicDecoded = "";
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
