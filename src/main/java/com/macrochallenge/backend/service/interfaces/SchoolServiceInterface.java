package com.macrochallenge.backend.service.interfaces;

import com.macrochallenge.backend.model.dto.SchoolsAndTestHeadersDTO;

import java.util.List;

public interface SchoolServiceInterface {
    public List<SchoolsAndTestHeadersDTO> getSchools(String userId);
}
