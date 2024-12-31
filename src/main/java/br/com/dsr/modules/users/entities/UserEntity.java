package br.com.dsr.modules.users.entities;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import br.com.dsr.modules.users.enums.RoleEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    @Email(message = "O campo [email] deve conter um e-mail válido")
    private String email;

    @Length(min = 6, message = "O campo [password] deve ser maior que 6")
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

}
