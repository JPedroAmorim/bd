package com.macrochallenge.backend.repositories;

import com.macrochallenge.backend.model.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SystemUser, Integer> {
    Optional<SystemUser> findById(String id);
}
