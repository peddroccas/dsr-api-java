package br.com.dsr.modules.financial.useCases;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dsr.modules.financial.entities.InvoicingEntity;
import br.com.dsr.modules.financial.repositories.InvoicingRepository;

@Service
public class FetchInvoicingsByStoreUseCase {

    @Autowired
    private InvoicingRepository invoicingRepository;

    public Map<Integer, Map<String, Double>> execute(UUID storeId) {
        var invoicings = this.invoicingRepository.findByStoreId(storeId).orElse(null);

        // Agrupa os invoicings por ano
        Map<Integer, Map<String, Double>> invoicingsPerYear = invoicings.stream()
                .collect(Collectors.groupingBy(
                        invoicing -> invoicing.getDate().getYear(),
                        Collectors.collectingAndThen(
                                Collectors.groupingBy(
                                        invoicing -> invoicing.getDate().getMonth(),
                                        Collectors.summingDouble(InvoicingEntity::getValue)),
                                monthMap -> {
                                    // Calcula o total por ano
                                    double yearlyTotal = monthMap.values().stream().mapToDouble(Double::doubleValue)
                                            .sum();
                                    // Cria um novo map que inclui o total anual
                                    Map<String, Double> result = new LinkedHashMap<>();
                                    monthMap.forEach((month, value) -> result.put(month.name(), value));
                                    result.put("TOTAL", yearlyTotal);
                                    return result;
                                })));

        return invoicingsPerYear;
    }

}
