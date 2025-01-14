package br.com.dsr.modules.financial.useCases;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dsr.exceptions.EntityFoundException;
import br.com.dsr.modules.financial.DTOs.InvoicingRecordDTO;
import br.com.dsr.modules.financial.entities.InvoicingEntity;
import br.com.dsr.modules.financial.repositories.InvoicingRepository;

@Service
public class CreateInvoicingUseCase {
    @Autowired
    private InvoicingRepository invoicingRepository;

    public InvoicingEntity execute(InvoicingRecordDTO invoicingRecordDTO) {
        var date = LocalDateTime.of(invoicingRecordDTO.year(), invoicingRecordDTO.month(), 1, 0, 0);

        this.invoicingRepository.findByStoreIdAndDate(invoicingRecordDTO.storeId(), date).ifPresent((invoicing) -> {
            throw new EntityFoundException("Invoicing");
        });

        var invoicingEntity = new InvoicingEntity();
        BeanUtils.copyProperties(invoicingRecordDTO, invoicingEntity);
        invoicingEntity.setDate(date);

        return this.invoicingRepository.save(invoicingEntity);
    }
}
