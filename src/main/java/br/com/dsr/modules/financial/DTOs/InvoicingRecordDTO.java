package br.com.dsr.modules.financial.DTOs;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record InvoicingRecordDTO(
        @NotBlank Double value,
        @NotBlank int month,
        @NotBlank int year,
        @NotBlank UUID storeId) {

}
