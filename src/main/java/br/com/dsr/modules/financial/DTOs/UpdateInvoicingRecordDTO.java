package br.com.dsr.modules.financial.DTOs;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record UpdateInvoicingRecordDTO(@NotNull Double value, @NotNull UUID id) {

}
