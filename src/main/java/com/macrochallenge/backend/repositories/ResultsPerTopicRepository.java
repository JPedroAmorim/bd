package com.macrochallenge.backend.repositories;

import com.macrochallenge.backend.model.ResultsPerTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultsPerTopicRepository extends JpaRepository<ResultsPerTopic, Integer> {
}
