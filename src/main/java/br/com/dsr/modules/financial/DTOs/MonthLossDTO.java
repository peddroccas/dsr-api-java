package br.com.dsr.modules.financial.DTOs;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonthLossDTO {
	private UUID id;
	private Double value;
	private Double percentage;
}
