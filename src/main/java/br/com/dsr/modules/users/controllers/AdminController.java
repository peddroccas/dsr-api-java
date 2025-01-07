package br.com.dsr.modules.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import br.com.dsr.modules.users.DTOs.AdminRecordDTO;
import br.com.dsr.modules.users.useCases.CreateAdminUseCase;
import br.com.dsr.modules.users.useCases.HasAnyAdminUseCase;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AdminController {
    @Autowired
    private HasAnyAdminUseCase hasAnyAdminUseCase;
    @Autowired
    private CreateAdminUseCase createAdminUseCase;

    @PostMapping("/first-admin")
    public ResponseEntity<Object> createFirstAdmin(@RequestBody @Valid AdminRecordDTO adminRecordDTO) {

        try {
            this.hasAnyAdminUseCase.execute();

            this.createAdminUseCase.execute(adminRecordDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> create(@RequestBody @Valid AdminRecordDTO adminRecordDTO) {

        try {
            this.createAdminUseCase.execute(adminRecordDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
