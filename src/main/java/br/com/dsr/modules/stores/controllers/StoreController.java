package br.com.dsr.modules.stores.controllers;

import org.springframework.web.bind.annotation.RestController;

import br.com.dsr.modules.stores.DTOs.StoreRecordDTO;
import br.com.dsr.modules.stores.useCases.CreateStoreUseCase;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/stores")
public class StoreController {

    @Autowired
    private CreateStoreUseCase createStoreUseCase;

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> create(@RequestBody @Valid StoreRecordDTO storeRecordDTO) {

        try {
            this.createStoreUseCase.execute(storeRecordDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
