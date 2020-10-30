package com.macrochallenge.backend.controllers;

import com.macrochallenge.backend.model.Question;
import com.macrochallenge.backend.model.dto.TestDTO;
import com.macrochallenge.backend.service.interfaces.TestServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/v1/tests")
@RestController
public class TestController {

    private final TestServiceInterface testService;

    @Autowired
    public TestController(TestServiceInterface testService) {
        this.testService = testService;
    }

    @GetMapping
    public List<Question> getTestQuestions(@RequestParam String testName, @RequestParam String testYear) {
        return testService.getTestQuestions(new TestDTO(testName, testYear));
    }
}
