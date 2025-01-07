package br.com.dsr.modules.stores.useCases;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dsr.exceptions.EntityFoundException;
import br.com.dsr.modules.stores.DTOs.StoreRecordDTO;
import br.com.dsr.modules.stores.entities.StoreEntity;
import br.com.dsr.modules.stores.repositories.StoreRepository;

@Service
public class CreateStoreUseCase {
    @Autowired
    private StoreRepository storeRepository;

    public StoreEntity execute(StoreRecordDTO storeRecordDTO) {
        this.storeRepository.findByName(storeRecordDTO.name()).ifPresent((store) -> {
            throw new EntityFoundException("Loja");
        });

        var storeEntity = new StoreEntity();
        BeanUtils.copyProperties(storeRecordDTO, storeEntity);

        return this.storeRepository.save(storeEntity);
    }
}
