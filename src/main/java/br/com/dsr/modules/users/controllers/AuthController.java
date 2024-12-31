package br.com.dsr.modules.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.dsr.modules.users.DTOs.AuthRecordDTO;
import br.com.dsr.modules.users.useCases.AuthenticateUseCase;
import jakarta.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    AuthenticateUseCase authenticateUseCase;

    @PostMapping("/auth")
    public ResponseEntity<Object> authenticate(@RequestBody @Valid AuthRecordDTO authRecordDTO) {
        try {

            var token = this.authenticateUseCase.execute(authRecordDTO);
            return ResponseEntity.ok().body(token);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

        }
    }
}
