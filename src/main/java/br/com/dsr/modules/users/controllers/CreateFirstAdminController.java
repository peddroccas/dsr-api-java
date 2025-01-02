package br.com.dsr.modules.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.dsr.modules.users.DTOs.UserRecordDTO;
import br.com.dsr.modules.users.useCases.CreateUserUseCase;
import br.com.dsr.modules.users.useCases.HasAnyAdminUseCase;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class CreateFirstAdminController {
    @Autowired
    private HasAnyAdminUseCase hasAnyAdminUseCase;
    private CreateUserUseCase createUserUseCase;

    @PostMapping("/first-admin")
    public ResponseEntity<Object> handle(@RequestBody @Valid UserRecordDTO userRecordDTO) {

        try {
            this.hasAnyAdminUseCase.execute();

            this.createUserUseCase.execute(userRecordDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }

}
