package com.macrochallenge.backend.controllers;

import com.macrochallenge.backend.model.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/login")
@RestController
public class LoginController {

    @PostMapping
    public ResponseEntity<String> addNewUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>("Usuário adicionado com sucesso", HttpStatus.OK);
    }

}
