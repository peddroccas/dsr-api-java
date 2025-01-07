package br.com.dsr.modules.stores.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dsr.modules.stores.repositories.StoreRepository;

@Service
public class FindStoresUseCase {
    @Autowired
    private StoreRepository storeRepository;

    public void execute() {
        var stores = this.storeRepository.findAll();
    }
}
