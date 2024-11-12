package br.com.dsr.modules.users.entities;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
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

    @NotBlank
    private String role;

    @OneToOne()
    @JoinColumn(name = "store_id", insertable = false, updatable = false)
    private StoreEntity storeEntity;

}
