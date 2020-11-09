package com.macrochallenge.backend.repositories;

import com.macrochallenge.backend.model.Results;
import com.macrochallenge.backend.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultsRepository extends JpaRepository<Results, Integer> {

}
