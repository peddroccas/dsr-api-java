package br.com.dsr.modules.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.dsr.modules.users.DTOs.UserRecordDTO;
import br.com.dsr.modules.users.useCases.CreateUserUseCase;
import jakarta.validation.Valid;

@RestController("/users")
@PreAuthorize("hasRole('ADMIN')")
public class CreateUserController {
    @Autowired
    private CreateUserUseCase createUserUseCase;

    @PostMapping()
    public ResponseEntity<Object> handle(@RequestBody @Valid UserRecordDTO userRecordDTO) {
        try {
            this.createUserUseCase.execute(userRecordDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }

}
