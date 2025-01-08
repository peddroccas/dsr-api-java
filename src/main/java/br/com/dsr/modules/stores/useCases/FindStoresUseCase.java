package br.com.dsr.modules.stores.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dsr.modules.stores.entities.StoreEntity;
import br.com.dsr.modules.stores.repositories.StoreRepository;

@Service
public class FindStoresUseCase {
    @Autowired
    private StoreRepository storeRepository;

    public List<StoreEntity> execute() {
        var stores = this.storeRepository.findAll();

        return stores;
    }
}
