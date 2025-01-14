package br.com.dsr.modules.financial.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dsr.modules.financial.DTOs.InvoicingRecordDTO;
import br.com.dsr.modules.financial.useCases.CreateInvoicingUseCase;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/invoicings")
public class InvoicingController {
    @Autowired
    private CreateInvoicingUseCase createInvoicingUseCase;

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
}
