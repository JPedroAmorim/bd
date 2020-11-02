package com.macrochallenge.backend.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.macrochallenge.backend.exceptions.NotFoundException;
import com.macrochallenge.backend.model.Results;
import com.macrochallenge.backend.model.ResultsPerTopic;
import com.macrochallenge.backend.model.Test;
import com.macrochallenge.backend.model.dto.GeneralResultsDTO;
import com.macrochallenge.backend.model.dto.ResultsDTO;
import com.macrochallenge.backend.model.dto.ResultsPerTopicDTO;
import com.macrochallenge.backend.repositories.ResultsPerTopicRepository;
import com.macrochallenge.backend.repositories.ResultsRepository;
import com.macrochallenge.backend.repositories.TestRepository;
import com.macrochallenge.backend.service.interfaces.ResultsServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class ResultsService implements ResultsServiceInterface {

    private final TestRepository testRepository;
    private final ResultsRepository resultsRepository;
    private final ResultsPerTopicRepository resultsPerTopicRepository;

    @Autowired
    public ResultsService(TestRepository testRepository, ResultsRepository resultsRepository,
                          ResultsPerTopicRepository resultsPerTopicRepository) {
        this.testRepository = testRepository;
        this.resultsRepository = resultsRepository;
        this.resultsPerTopicRepository = resultsPerTopicRepository;
    }



    @Override
    public void addNewResult(ResultsDTO resultsDTO) {
        Optional<Test> testForResultOptional = testRepository.findByNameAndYear(resultsDTO.getTestName(),
                resultsDTO.getTestYear());

        if (!testForResultOptional.isPresent()) {
            throw new NotFoundException("Prova não encontrada");
        }

        Test testForResult = testForResultOptional.get();

        Double totalPercentageOfCorrectAnswers = Double.valueOf(resultsDTO.getTotalPercentageOfCorrectAnswers());
        Integer totalNumberOfQuestions = Integer.valueOf(resultsDTO.getTotalNumberOfQuestions());
        Integer totalNumberOfCorrectAnswers = Integer.valueOf(resultsDTO.getTotalNumberOfCorrectAnswers());
        Integer totalNumberOfAnsweredQuestions = Integer.valueOf(resultsDTO.getTotalNumberOfAnsweredQuestions());

        Gson gson = new Gson();

        String resultsPerTopicJSON = resultsDTO.getResultsPerTopic();

        Type resultsPerTopicListType = new TypeToken<ArrayList<ResultsPerTopicDTO>>(){}.getType();

        List<ResultsPerTopicDTO> resultsPerTopicDTOList = gson.fromJson(resultsPerTopicJSON, resultsPerTopicListType);

        Results resultsEntity = new Results(testForResult, totalPercentageOfCorrectAnswers,
                totalNumberOfAnsweredQuestions, totalNumberOfQuestions, totalNumberOfCorrectAnswers,
                resultsDTO.getCorrectAnswers(), resultsDTO.getWrongAnswers(), resultsDTO.getAnsweredQuestions());


        List<ResultsPerTopic> resultsPerTopicList = new ArrayList<>();

        for (ResultsPerTopicDTO resultsPerTopicDTO : resultsPerTopicDTOList) {

            Double totalPercentageOfCorrectAnswersForTopic =
                    Double.valueOf(resultsPerTopicDTO.getTotalPercentageOfCorrectAnswers());
            Integer totalNumberOfQuestionsForTopic =
                    Integer.valueOf(resultsPerTopicDTO.getTotalNumberOfQuestions());
            Integer totalNumberOfCorrectAnswersForTopic =
                    Integer.valueOf(resultsPerTopicDTO.getTotalNumberOfCorrectAnswers());
            Integer totalNumberOfAnsweredQuestionsForTopic =
                    Integer.valueOf(resultsPerTopicDTO.getTotalNumberOfAnsweredQuestions());

            ResultsPerTopic resultsPerTopic = new ResultsPerTopic(resultsEntity, resultsPerTopicDTO.getTopic(),
                    totalPercentageOfCorrectAnswersForTopic, totalNumberOfCorrectAnswersForTopic,
                    totalNumberOfAnsweredQuestionsForTopic, totalNumberOfQuestionsForTopic);

            resultsPerTopicList.add(resultsPerTopic);
        }


        resultsEntity.getResultsPerTopics().addAll(resultsPerTopicList);

        resultsRepository.save(resultsEntity);
    }

    @Override
    public GeneralResultsDTO getAccumulatedResults() {
        List<Results> allResults = resultsRepository.findAll();

        int accumulatedNumberOfQuestions = allResults.stream()
                .mapToInt(Results::getTotalNumberOfQuestions)
                .sum();

        int accumulatedNumberOfCorrectAnswers = allResults.stream()
                .mapToInt(Results::getTotalNumberOfCorrectAnswers)
                .sum();

        Double totalPercentageOfCorrectAnswers = round((double) accumulatedNumberOfQuestions
                / (double) accumulatedNumberOfCorrectAnswers);

        Map<String, ResultsPerTopicDTO> resultsPerTopicDTOMap = new HashMap<>();

        for (Results results : allResults) {
            int size = results.getResultsPerTopics().size();
            for(ResultsPerTopic resultsPerTopic : results.getResultsPerTopics()) {
                if (!resultsPerTopicDTOMap.containsKey(resultsPerTopic.getTopic())) {
                    ResultsPerTopicDTO resultsPerTopicDTO = new ResultsPerTopicDTO(resultsPerTopic.getTopic(),
                            String.valueOf(resultsPerTopic.getTotalPercentageOfCorrectAnswers()),
                            String.valueOf(resultsPerTopic.getTotalNumberOfCorrectAnswers()),
                            String.valueOf(resultsPerTopic.getTotalNumberOfAnsweredQuestions()),
                            String.valueOf(resultsPerTopic.getTotalNumberOfQuestions()));

                    resultsPerTopicDTOMap.put(resultsPerTopic.getTopic(), resultsPerTopicDTO);
                } else {
                    ResultsPerTopicDTO entry = resultsPerTopicDTOMap.get(resultsPerTopic.getTopic());

                    Double recalculatedValueForCorrectAnswers =
                            Double.parseDouble(entry.getTotalNumberOfCorrectAnswers()) +
                                    resultsPerTopic.getTotalNumberOfCorrectAnswers();

                    Double recalculatedValueForAnsweredQuestions =
                            Double.parseDouble(entry.getTotalNumberOfAnsweredQuestions()) +
                            resultsPerTopic.getTotalNumberOfAnsweredQuestions();

                    Double recalculatedValueForTotalNumberOfQuestions =
                            Double.parseDouble(entry.getTotalNumberOfQuestions()) +
                                    resultsPerTopic.getTotalNumberOfQuestions();

                    entry.setTotalNumberOfCorrectAnswers(String.valueOf(recalculatedValueForCorrectAnswers));
                    entry.setTotalNumberOfAnsweredQuestions(String.valueOf(recalculatedValueForAnsweredQuestions));
                    entry.setTotalNumberOfQuestions(String.valueOf(recalculatedValueForTotalNumberOfQuestions));

                    double totalPercentageOfCorrectAnswersForTopic = 0.0;

                    if (recalculatedValueForTotalNumberOfQuestions != 0) {
                        totalPercentageOfCorrectAnswersForTopic = (recalculatedValueForCorrectAnswers
                                / recalculatedValueForTotalNumberOfQuestions);
                    }

                    totalPercentageOfCorrectAnswers = round(totalPercentageOfCorrectAnswers);

                    entry.setTotalPercentageOfCorrectAnswers(String.valueOf(totalPercentageOfCorrectAnswersForTopic));

                    resultsPerTopicDTOMap.put(resultsPerTopic.getTopic(), entry);
                }
            }
        }

        List<ResultsPerTopicDTO> resultsPerTopicDTOList = new ArrayList<>();

        for (Map.Entry<String, ResultsPerTopicDTO> entry : resultsPerTopicDTOMap.entrySet()) {
            resultsPerTopicDTOList.add(entry.getValue());
        }


        GeneralResultsDTO generalResultsDTO = new GeneralResultsDTO(String.valueOf(totalPercentageOfCorrectAnswers),
                String.valueOf(accumulatedNumberOfQuestions), String.valueOf(accumulatedNumberOfCorrectAnswers),
                resultsPerTopicDTOList);


        return  generalResultsDTO;
    }

    private double round(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
