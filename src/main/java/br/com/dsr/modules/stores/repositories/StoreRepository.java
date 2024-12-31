package br.com.dsr.modules.stores.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dsr.modules.stores.entities.StoreEntity;

public interface StoreRepository extends JpaRepository<StoreEntity, UUID> {
}
