package br.com.dsr.modules.users.DTOs;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerResponseDTO {
    @NotBlank
    UUID id;

    @NotBlank
    String name;

    @NotBlank
    @Email
    String email;

    @NotBlank
    UUID storeId;
}
