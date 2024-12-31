package br.com.dsr.modules.users.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRecordDTO(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String role) {
}