package br.com.dsr.modules.financial.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dsr.modules.financial.DTOs.InvoicingRecordDTO;
import br.com.dsr.modules.financial.useCases.CreateInvoicingUseCase;
import br.com.dsr.modules.financial.useCases.FetchInvoicingsByStoreUseCase;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/invoicings")
public class InvoicingController {
    @Autowired
    private CreateInvoicingUseCase createInvoicingUseCase;
    @Autowired
    private FetchInvoicingsByStoreUseCase fetchInvoicingsByStoreUseCase;

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> create(@RequestBody @Valid InvoicingRecordDTO invoicingRecordDTO) {
        try {
            this.createInvoicingUseCase.execute(invoicingRecordDTO);
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{storeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> fetchByStore(@PathVariable UUID storeId) {

        try {
            var invoicings = this.fetchInvoicingsByStoreUseCase.execute(storeId);

            return ResponseEntity.ok().body(invoicings);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
