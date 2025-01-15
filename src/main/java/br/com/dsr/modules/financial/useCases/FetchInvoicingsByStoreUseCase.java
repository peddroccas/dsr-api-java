package br.com.dsr.modules.financial.useCases;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dsr.modules.financial.repositories.InvoicingRepository;

@Service
public class FetchInvoicingsByStoreUseCase {

        @Autowired
        private InvoicingRepository invoicingRepository;

        public Map<Object, Object> execute(UUID storeId) {
                var invoicings = this.invoicingRepository.findByStoreId(storeId).orElse(null);

                Map<Object, Object> invoicingsPerYear = invoicings.stream()
                                .collect(Collectors.groupingBy(
                                                invoicing -> invoicing.getDate().getYear(),
                                                Collectors.collectingAndThen(
                                                                Collectors.groupingBy(
                                                                                invoicing -> invoicing.getDate()
                                                                                                .getMonth().name(),
                                                                                Collectors.reducing(
                                                                                                new LinkedHashMap<>(),
                                                                                                invoicing -> {
                                                                                                        // Cria um mapa
                                                                                                        // com os campos
                                                                                                        // id e value
                                                                                                        Map<String, Object> invoicingData = new LinkedHashMap<>();
                                                                                                        invoicingData.put(
                                                                                                                        "id",
                                                                                                                        invoicing.getId()
                                                                                                                                        .toString());
                                                                                                        invoicingData.put(
                                                                                                                        "value",
                                                                                                                        invoicing.getValue());
                                                                                                        return invoicingData;
                                                                                                },
                                                                                                (map1, map2) -> map2 // Mantém
                                                                                                                     // o
                                                                                                                     // último
                                                                                                                     // mapeamento,
                                                                                                                     // se
                                                                                                                     // houver
                                                                                                                     // mais
                                                                                                                     // de
                                                                                                                     // um
                                                                                )),
                                                                monthMap -> {
                                                                        // Adiciona o total anual
                                                                        double yearlyTotal = monthMap.values().stream()
                                                                                        .mapToDouble(data -> (Double) data
                                                                                                        .get("value"))
                                                                                        .sum();

                                                                        // Adiciona o total anual
                                                                        monthMap.put("TOTAL",
                                                                                        Map.of("value", yearlyTotal));
                                                                        return monthMap;
                                                                })));

                return invoicingsPerYear;
        }

}
