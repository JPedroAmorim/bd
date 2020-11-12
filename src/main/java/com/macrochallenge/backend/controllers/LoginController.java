package com.macrochallenge.backend.controllers;

import com.macrochallenge.backend.model.dto.ExistsDTO;
import com.macrochallenge.backend.model.dto.UserDTO;
import com.macrochallenge.backend.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/login")
@RestController
public class LoginController {

    //
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<String> addNewUser(@RequestBody UserDTO userDTO) {
        loginService.addNewUser(userDTO.getUserId());
        return new ResponseEntity<>("Usuário adicionado com sucesso", HttpStatus.OK);
    }

    @GetMapping
    public ExistsDTO checkUser(@RequestParam String userId) {
        return loginService.checkUserExists(userId);
    }

}
