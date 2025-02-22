package br.com.dsr.modules.stores.controllers;

import org.springframework.web.bind.annotation.RestController;

import br.com.dsr.modules.stores.DTOs.StoreRecordDTO;
import br.com.dsr.modules.stores.entities.StoreEntity;
import br.com.dsr.modules.stores.useCases.CreateStoreUseCase;
import br.com.dsr.modules.stores.useCases.FindStoresUseCase;
import br.com.dsr.modules.stores.useCases.UpdateStoreUseCase;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/stores")
public class StoreController {

    @Autowired
    private CreateStoreUseCase createStoreUseCase;
    @Autowired
    private FindStoresUseCase findStoresUseCase;
    @Autowired
    private UpdateStoreUseCase updateStoreUseCase;

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

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> findAll() {
        try {
            var stores = this.findStoresUseCase.execute();

            return ResponseEntity.ok().body(stores);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> update(@RequestBody @Valid StoreEntity storeEntity) {
        try {
            this.updateStoreUseCase.execute(storeEntity);
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
