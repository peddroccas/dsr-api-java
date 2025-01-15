package br.com.dsr.modules.financial.useCases;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dsr.exceptions.EntityFoundException;
import br.com.dsr.modules.financial.DTOs.LossRecordDTO;
import br.com.dsr.modules.financial.entities.LossEntity;
import br.com.dsr.modules.financial.repositories.LossRepository;

@Service
public class CreateLossUseCase {
    @Autowired
    private LossRepository lossRepository;

    public LossEntity execute(LossRecordDTO lossRecordDTO) {
        var date = LocalDateTime.of(lossRecordDTO.year(), lossRecordDTO.month(), 1, 0, 0);

        this.lossRepository.findByStoreIdAndDate(lossRecordDTO.storeId(), date).ifPresent((loss) -> {
            throw new EntityFoundException("loss");
        });

        var lossEntity = new LossEntity();
        BeanUtils.copyProperties(lossRecordDTO, lossEntity);
        lossEntity.setDate(date);

        return this.lossRepository.save(lossEntity);
    }
}
