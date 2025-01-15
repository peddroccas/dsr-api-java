package br.com.dsr.modules.financial.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dsr.modules.financial.entities.LossEntity;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

public interface LossRepository extends JpaRepository<LossEntity, UUID> {
    Optional<LossEntity> findByStoreIdAndDate(UUID storeId, LocalDateTime date);

    Optional<List<LossEntity>> findByStoreId(UUID storeId);

}
