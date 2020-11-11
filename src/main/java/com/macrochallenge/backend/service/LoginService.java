package com.macrochallenge.backend.service;

import com.macrochallenge.backend.exceptions.AlreadyExistsException;
import com.macrochallenge.backend.model.SystemUser;
import com.macrochallenge.backend.model.dto.ExistsDTO;
import com.macrochallenge.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addNewUser(String userId) {
        Optional<SystemUser> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            throw new AlreadyExistsException("Usuário já existe no sistema");
        }

        SystemUser userEntity = new SystemUser(userId);

        userRepository.save(userEntity);
    }

    public ExistsDTO checkUserExists(String userId) {
        Optional<SystemUser> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            return new ExistsDTO("true");
        }

        return new ExistsDTO("false");
    }
}
