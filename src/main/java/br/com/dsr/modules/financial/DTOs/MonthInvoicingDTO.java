package br.com.dsr.modules.financial.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthInvoicingDTO {
	private double value;
	private double diary;
	private Double growthLastYear;
	private Double growthLastMonth;
}
