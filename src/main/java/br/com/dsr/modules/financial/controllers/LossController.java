package br.com.dsr.modules.financial.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dsr.modules.financial.DTOs.LossRecordDTO;
import br.com.dsr.modules.financial.DTOs.UpdateLossRecordDTO;
import br.com.dsr.modules.financial.useCases.CreateLossUseCase;
import br.com.dsr.modules.financial.useCases.DeleteLossUseCase;
import br.com.dsr.modules.financial.useCases.FetchLossesByStoreUseCase;
import br.com.dsr.modules.financial.useCases.UpdateLossUseCase;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/losses")
public class LossController {
    @Autowired
    private CreateLossUseCase createLossUseCase;
    @Autowired
    private FetchLossesByStoreUseCase fetchLossesByStoreUseCase;
    @Autowired
    private UpdateLossUseCase updateLossUseCase;
    @Autowired
    private DeleteLossUseCase deleteLossUseCase;

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> create(@RequestBody @Valid LossRecordDTO lossRecordDTO) {
        try {
            this.createLossUseCase.execute(lossRecordDTO);
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{storeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> fetchByStore(@PathVariable UUID storeId) {

        try {
            var losses = this.fetchLossesByStoreUseCase.execute(storeId);

            return ResponseEntity.ok().body(losses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> update(@RequestBody @Valid UpdateLossRecordDTO updateLossRecordDTO) {
        try {
            this.updateLossUseCase.execute(updateLossRecordDTO);
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        try {
            this.deleteLossUseCase.execute(id);
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
