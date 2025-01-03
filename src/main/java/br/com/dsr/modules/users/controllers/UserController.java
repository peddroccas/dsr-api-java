package br.com.dsr.modules.users.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dsr.modules.users.DTOs.AuthRecordDTO;
import br.com.dsr.modules.users.DTOs.UpdatePasswordDTO;
import br.com.dsr.modules.users.DTOs.UserRecordDTO;
import br.com.dsr.modules.users.useCases.AuthenticateUseCase;
import br.com.dsr.modules.users.useCases.DeleteUserUseCase;
import br.com.dsr.modules.users.useCases.ProfileUseCase;
import br.com.dsr.modules.users.useCases.UpdatePasswordUseCase;
import br.com.dsr.modules.users.useCases.UpdateUserUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private AuthenticateUseCase authenticateUseCase;
    @Autowired
    private ProfileUseCase profileUseCase;
    @Autowired
    private UpdateUserUseCase updateUserUseCase;
    @Autowired
    private DeleteUserUseCase deleteUserUseCase;
    @Autowired
    private UpdatePasswordUseCase updatePasswordUseCase;

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

    @PostMapping("/auth")
    public ResponseEntity<Object> authenticate(@RequestBody @Valid AuthRecordDTO authRecordDTO) {
        try {

            var token = this.authenticateUseCase.execute(authRecordDTO);
            return ResponseEntity.ok().body(token);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

        }
    }

    @PutMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> update(@RequestBody UserRecordDTO userRecordDTO) {
        try {
            updateUserUseCase.execute(userRecordDTO);
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        try {
            deleteUserUseCase.execute(id);
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/users")
    public ResponseEntity<Object> updatePassword(HttpServletRequest request,
            @RequestBody UpdatePasswordDTO updatePasswordDTO) {
        var id = UUID.fromString(request.getAttribute("id").toString());

        try {
            updatePasswordUseCase.execute(id, updatePasswordDTO.password());
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
