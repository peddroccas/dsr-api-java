package br.com.dsr.modules.financial.DTOs;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record LossRecordDTO(
                @NotNull Double value,
                @NotNull int month,
                @NotNull int year,
                @NotNull UUID storeId) {

}
