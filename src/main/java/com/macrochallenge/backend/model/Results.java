package com.macrochallenge.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Results {
    @Id
    @SequenceGenerator(name = "id", sequenceName = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    private Integer id;

    @ManyToOne
   private Test test;

    //qqaa

    @ManyToOne
    private SystemUser user;

    @OneToMany(mappedBy = "result", cascade = CascadeType.ALL)
    private List<ResultsPerTopic> resultsPerTopics = new ArrayList<>();

    @NonNull
    private Double totalPercentageOfCorrectAnswers;

    @NonNull
    private Integer totalNumberOfAnsweredQuestions;

    @NonNull
    private Integer totalNumberOfQuestions;

    @NonNull
    private Integer totalNumberOfCorrectAnswers;

    @NonNull
    private String correctAnswers;

    @NonNull
    private String wrongAnswers;

    @NonNull
    private String answeredQuestions;

    public Results(Test test, Double totalPercentageOfCorrectAnswers, Integer totalNumberOfAnsweredQuestions,
                   Integer totalNumberOfQuestions, Integer totalNumberOfCorrectAnswers, String correctAnswers,
                   String wrongAnswers, String answeredQuestions) {
        this.test = test;
        this.totalPercentageOfCorrectAnswers = totalPercentageOfCorrectAnswers;
        this.totalNumberOfQuestions = totalNumberOfQuestions;
        this.totalNumberOfCorrectAnswers = totalNumberOfCorrectAnswers;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
        this.totalNumberOfAnsweredQuestions = totalNumberOfAnsweredQuestions;
        this.answeredQuestions = answeredQuestions;
    }
}
