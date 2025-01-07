package br.com.dsr.modules.stores.DTOs;

import jakarta.validation.constraints.NotBlank;

public record StoreRecordDTO(
        @NotBlank String name) {

}
