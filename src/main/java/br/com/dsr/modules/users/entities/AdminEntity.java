package br.com.dsr.modules.users.entities;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity(name = "admins")
@EqualsAndHashCode(callSuper = true)
public class AdminEntity extends UserEntity {
}
