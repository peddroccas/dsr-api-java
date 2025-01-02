package br.com.dsr.modules.users.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dsr.modules.users.entities.AdminEntity;

public interface AdminRepository extends JpaRepository<AdminEntity, UUID> {
    Optional<AdminEntity> findByEmail(String email);
}
