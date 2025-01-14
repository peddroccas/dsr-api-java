package br.com.dsr.modules.financial.useCases;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dsr.modules.financial.DTOs.UpdateInvoicingRecordDTO;
import br.com.dsr.modules.financial.entities.InvoicingEntity;
import br.com.dsr.modules.financial.repositories.InvoicingRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UpdateInvoicingUseCase {
    @Autowired
    private InvoicingRepository invoicingRepository;

    public InvoicingEntity execute(UpdateInvoicingRecordDTO updateInvoicingRecordDTO) {
        var invoicing = this.invoicingRepository.findById(updateInvoicingRecordDTO.id()).orElseThrow(() -> {
            throw new EntityNotFoundException();
        });

        var invoicingEntity = new InvoicingEntity();

        BeanUtils.copyProperties(invoicing, invoicingEntity);
        invoicingEntity.setValue(updateInvoicingRecordDTO.value());

        return this.invoicingRepository.save(invoicingEntity);
    }
}
