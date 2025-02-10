package br.com.dsr.modules.users.DTOs;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateManagerRecordDTO(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotNull UUID storeId) {

}
