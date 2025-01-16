package br.com.dsr.modules.financial.useCases;

import java.time.Month;
import java.time.Year;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dsr.modules.financial.DTOs.MonthLossDTO;
import br.com.dsr.modules.financial.entities.InvoicingEntity;
import br.com.dsr.modules.financial.repositories.InvoicingRepository;
import br.com.dsr.modules.financial.repositories.LossRepository;

@Service
public class FetchLossesByStoreUseCase {

    @Autowired
    private LossRepository lossRepository;
    @Autowired
    private InvoicingRepository invoicingRepository;

    public Map<Year, Map<Month, MonthLossDTO>> execute(UUID storeId) {

        // Carrega as perdas
        var losses = this.lossRepository.findByStoreId(storeId).orElse(null);

        // Agrupamento das perdas por ano e mês
        Map<Year, Map<Month, MonthLossDTO>> lossesPerYear = losses.stream()
                .collect(Collectors.groupingBy(
                        loss -> Year.of(loss.getDate().getYear()),
                        Collectors.collectingAndThen(
                                Collectors.groupingBy(
                                        loss -> loss.getDate().getMonth(),
                                        Collectors.reducing(
                                                null,
                                                loss -> {
                                                    // Criação do DTO com os campos id, value
                                                    UUID id = loss.getId();
                                                    Double value = loss.getValue();

                                                    // Obtém o valor de faturamento para o mês correspondente
                                                    Optional<InvoicingEntity> invoicing = this.invoicingRepository
                                                            .findByStoreIdAndDate(storeId, loss.getDate());

                                                    // Calcula a porcentagem (se invoicingValue não for zero)
                                                    Double percentage = invoicing.isPresent()
                                                            ? (value / invoicing.get().getValue()) * 100
                                                            : 0.0;

                                                    MonthLossDTO monthLossDTO = MonthLossDTO.builder()
                                                            .id(id)
                                                            .value(value)
                                                            .percentage(percentage)
                                                            .build();

                                                    return monthLossDTO;
                                                },
                                                (dto1, dto2) -> dto2)),
                                monthMap -> {
                                    // Adiciona o total anual
                                    double yearlyTotal = monthMap.values().stream()
                                            .mapToDouble(MonthLossDTO::getValue)
                                            .sum();

                                    // Adiciona o total anual
                                    monthMap.put(Month.DECEMBER, MonthLossDTO.builder().id(UUID.randomUUID())
                                            .value(yearlyTotal).percentage(0.0).build());
                                    return monthMap;
                                })));
        return lossesPerYear;
    }
}
