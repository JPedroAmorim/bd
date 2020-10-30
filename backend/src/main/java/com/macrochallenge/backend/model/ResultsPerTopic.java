package com.macrochallenge.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class ResultsPerTopic {
    @Id
    @SequenceGenerator(name = "id", sequenceName = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    private Integer id;

    @ManyToOne
    private Results result;

    @NonNull
    private String topic;

    @NonNull
    private Double totalPercentageOfCorrectAnswers;

    @NonNull
    private Integer totalNumberOfCorrectAnswers;

    @NonNull
    private Integer totalNumberOfAnsweredQuestions;

    @NonNull
    private Integer totalNumberOfQuestions;

    public ResultsPerTopic(Results result, String topic, Double totalPercentageOfCorrectAnswers,
                           Integer totalNumberOfCorrectAnswers, Integer totalNumberOfQuestions,
                           Integer totalNumberOfAnsweredQuestions) {
        this.result = result;
        this.topic = topic;
        this.totalPercentageOfCorrectAnswers = totalPercentageOfCorrectAnswers;
        this.totalNumberOfCorrectAnswers = totalNumberOfCorrectAnswers;
        this.totalNumberOfAnsweredQuestions = totalNumberOfAnsweredQuestions;
        this.totalNumberOfQuestions = totalNumberOfQuestions;
    }
}
