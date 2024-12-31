package br.com.dsr.modules.users.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dsr.modules.users.entities.ManagerEntity;

public interface ManagerRepository extends JpaRepository<ManagerEntity, UUID> {
}
