package br.com.dsr.modules.financial.DTOs;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YearInvoicingDTO {
	private Map<String, MonthInvoicingDTO> months;
}
