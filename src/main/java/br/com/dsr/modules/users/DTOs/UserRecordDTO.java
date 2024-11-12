package br.com.dsr.modules.users.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRecordDTO(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank @Pattern(regexp = "^(ADMIN|MANAGER)$", message = "Role must be ADMIN or MANAGER") String role) {
}
