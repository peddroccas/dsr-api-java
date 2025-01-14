package br.com.dsr.modules.financial.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dsr.modules.financial.repositories.InvoicingRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DeleteInvoicingUseCase {
    @Autowired
    private InvoicingRepository invoicingRepository;

    public void execute(UUID id) {
        this.invoicingRepository.findById(id)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException();
                });

        this.invoicingRepository.deleteById(id);
    }
}
