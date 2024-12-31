package br.com.dsr.modules.users.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import br.com.dsr.modules.users.DTOs.ProfileResponseDTO;
import br.com.dsr.modules.users.DTOs.UserRecordDTO;
import br.com.dsr.modules.users.useCases.CreateUserUseCase;
import br.com.dsr.modules.users.useCases.HasAnyAdminUseCase;
import br.com.dsr.modules.users.useCases.ProfileUseCase;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UserController {

    @Autowired
    private CreateUserUseCase createUserUseCase;
    @Autowired
    private HasAnyAdminUseCase hasAnyAdminUseCase;
    @Autowired
    ProfileUseCase profileUseCase;

    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> create(@RequestBody @Valid UserRecordDTO userRecordDTO) {
        try {
            this.createUserUseCase.execute(userRecordDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }

    @PostMapping("/first-admin")
    public ResponseEntity<Object> createFirstAdmin(@RequestBody @Valid UserRecordDTO userRecordDTO) {

        try {
            this.hasAnyAdminUseCase.execute();

            this.createUserUseCase.execute(userRecordDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }

    @GetMapping("/profile")
    public ResponseEntity<Object> profile(HttpServletRequest request) {
        var id = request.getAttribute("id");
        try {
            var profile = profileUseCase.execute(UUID.fromString(id.toString()));
            return ResponseEntity.ok().body(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
