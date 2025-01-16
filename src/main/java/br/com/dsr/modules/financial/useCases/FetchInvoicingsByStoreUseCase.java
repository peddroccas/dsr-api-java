package br.com.dsr.modules.financial.useCases;

import java.time.Month;
import java.time.Year;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dsr.modules.financial.DTOs.MonthInvoicingDTO;
import br.com.dsr.modules.financial.repositories.InvoicingRepository;

@Service
public class FetchInvoicingsByStoreUseCase {

    @Autowired
    private InvoicingRepository invoicingRepository;

    public Map<Year, Map<Month, MonthInvoicingDTO>> execute(UUID storeId) {
        var invoicings = this.invoicingRepository.findByStoreId(storeId).orElse(null);

        // Organize os faturamentos por ano e por mês
        Map<Year, Map<Month, MonthInvoicingDTO>> invoicingsPerYear = invoicings.stream()
                .collect(Collectors.groupingBy(
                        invoicing -> Year.of(invoicing.getDate().getYear()),
                        Collectors.groupingBy(
                                invoicing -> invoicing.getDate().getMonth(),
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        invoicingList -> {
                                            // Calcula os dados para cada mês
                                            double totalValue = invoicingList.stream()
                                                    .mapToDouble(invoicing -> invoicing.getValue()).sum();
                                            double diaryValue = totalValue /
                                                    invoicingList.get(0).getDate().getMonth().length(
                                                            invoicingList.get(0).getDate().getYear() % 4 == 0);

                                            MonthInvoicingDTO monthInvoicingDTO = new MonthInvoicingDTO();
                                            monthInvoicingDTO.setValue(totalValue);
                                            monthInvoicingDTO.setDiary(diaryValue);
                                            return monthInvoicingDTO;
                                        }))));

        // Calcula o crescimento para cada mês
        invoicingsPerYear.forEach((year, months) -> {
            months.forEach((month, invoicing) -> {
                double currentValue = (double) invoicing.getValue();

                // Crescimento em relação ao mesmo mês do último ano
                if (invoicingsPerYear.containsKey(year.minusYears(1)) &&
                        invoicingsPerYear.get(year.minusYears(1)).containsKey(month)) {
                    double lastYearValue = (double) invoicingsPerYear.get(year.minusYears(1)).get(month).getValue();
                    double growthLastYear = ((currentValue - lastYearValue) / lastYearValue) * 100;
                    invoicing.setGrowthLastYear(growthLastYear);
                } else {
                    invoicing.setGrowthLastYear(null); // Sem dados do último ano
                }

                // Crescimento em relação ao último mês
                Month previousMonth = getPreviousMonth(month, year, invoicingsPerYear);
                if (previousMonth != null) {
                    Year previousYear = month.equals(Month.JANUARY) ? year.minusYears(1) : year;
                    if (invoicingsPerYear.containsKey(previousYear) &&
                            invoicingsPerYear.get(previousYear).containsKey(previousMonth)) {
                        double lastMonthValue = (double) invoicingsPerYear.get(previousYear).get(previousMonth)
                                .getValue();
                        double growthLastMonth = ((currentValue - lastMonthValue) / lastMonthValue) * 100;
                        invoicing.setGrowthLastMonth(growthLastMonth);
                    } else {
                        invoicing.setGrowthLastMonth(null); // Sem dados do mês anterior
                    }
                } else {
                    invoicing.setGrowthLastMonth(null); // Sem dados do mês anterior
                }
            });
        });

        return invoicingsPerYear;
    }

    // Método auxiliar para obter o mês anterior, considerando a transição de ano
    private Month getPreviousMonth(Month currentMonth, Year currentYear,
            Map<Year, Map<Month, MonthInvoicingDTO>> invoicingsPerYear) {
        try {
            var previous = currentMonth.minus(1);

            // Verifica se o mês anterior é do ano anterior
            if (previous == java.time.Month.DECEMBER) {
                Year previousYear = currentYear.minusYears(1);
                if (invoicingsPerYear.containsKey(previousYear)) {
                    return Month.DECEMBER; // Último mês do ano anterior
                } else {
                    return null; // Sem dados para o ano anterior
                }
            }
            return previous;
        } catch (Exception e) {
            return null; // Mês inválido ou sem anterior
        }
    }
}
