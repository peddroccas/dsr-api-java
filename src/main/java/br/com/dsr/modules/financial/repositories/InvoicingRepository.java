package br.com.dsr.modules.financial.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dsr.modules.financial.entities.InvoicingEntity;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

public interface InvoicingRepository extends JpaRepository<InvoicingEntity, UUID> {
    Optional<InvoicingEntity> findByStoreIdAndDate(UUID storeId, LocalDateTime date);

    Optional<List<InvoicingEntity>> findByStoreId(UUID storeId);

}
