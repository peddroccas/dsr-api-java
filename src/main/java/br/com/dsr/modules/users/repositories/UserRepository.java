package br.com.dsr.modules.users.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dsr.modules.users.entities.UserEntity;
import br.com.dsr.modules.users.enums.RoleEnum;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);

    Optional<List<UserEntity>> findByRole(RoleEnum role);

}
