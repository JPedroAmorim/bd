package com.macrochallenge.backend.controllers;

import com.macrochallenge.backend.model.Question;
import com.macrochallenge.backend.service.QuestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/v1/questions")
@RestController
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public List<Question> getQuestionsForTopicAndSchool(@RequestParam String schoolName, @RequestParam String topic) {
        return questionService.getQuestionsForTopicAndSchool(schoolName, topic);
    }
}
