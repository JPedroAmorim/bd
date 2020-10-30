package com.macrochallenge.backend.repositories;

import com.macrochallenge.backend.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<School, Integer> {
   School findOneById(Integer id);
   Optional<School> findByName(String name);
}
