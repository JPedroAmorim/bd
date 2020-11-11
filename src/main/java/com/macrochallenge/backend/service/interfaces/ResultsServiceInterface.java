package com.macrochallenge.backend.service.interfaces;

import com.macrochallenge.backend.model.dto.GeneralResultsDTO;
import com.macrochallenge.backend.model.dto.ResultsDTO;

public interface ResultsServiceInterface {
    public void addNewResult(ResultsDTO resultsDTO, String userId);
    public GeneralResultsDTO getAccumulatedResults(String userId);
}
