package br.com.dsr.modules.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import br.com.dsr.modules.users.DTOs.UserRecordDTO;
import br.com.dsr.modules.users.entities.UserEntity;
import br.com.dsr.modules.users.useCases.CreateUserUseCase;
import br.com.dsr.modules.users.useCases.HasAnyAdminUseCase;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserController {

    @Autowired
    private CreateUserUseCase createUserUseCase;
    @Autowired
    private HasAnyAdminUseCase hasAnyAdminUseCase;

    @PostMapping("/users")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<UserEntity> create(@RequestBody @Valid UserRecordDTO userRecordDTO) {

        var response = this.createUserUseCase.execute(userRecordDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/first-admin")
    public ResponseEntity<UserEntity> createFirstAdmin(@RequestBody @Valid UserRecordDTO userRecordDTO) {

        this.hasAnyAdminUseCase.execute();

        var response = this.createUserUseCase.execute(userRecordDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
