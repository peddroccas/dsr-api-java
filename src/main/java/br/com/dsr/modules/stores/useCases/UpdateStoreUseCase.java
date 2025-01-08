package br.com.dsr.modules.stores.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dsr.exceptions.EntityFoundException;
import br.com.dsr.modules.stores.entities.StoreEntity;
import br.com.dsr.modules.stores.repositories.StoreRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UpdateStoreUseCase {
    @Autowired
    private StoreRepository storeRepository;

    public StoreEntity execute(StoreEntity storeEntity) {
        this.storeRepository.findById(storeEntity.getId()).orElseThrow(() -> {
            throw new EntityNotFoundException();
        });

        this.storeRepository.findByName(storeEntity.getName()).ifPresent((store) -> {
            throw new EntityFoundException("Loja");
        });

        return this.storeRepository.save(storeEntity);

    }
}
