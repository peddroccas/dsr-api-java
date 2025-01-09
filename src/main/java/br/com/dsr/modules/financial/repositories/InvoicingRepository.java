package br.com.dsr.modules.financial.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dsr.modules.financial.entities.InvoicingEntity;

public interface InvoicingRepository extends JpaRepository<InvoicingEntity, UUID> {
}
