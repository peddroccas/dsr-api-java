package br.com.dsr.modules.financial.useCases;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dsr.modules.financial.DTOs.UpdateLossRecordDTO;
import br.com.dsr.modules.financial.entities.LossEntity;
import br.com.dsr.modules.financial.repositories.LossRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UpdateLossUseCase {
    @Autowired
    private LossRepository lossRepository;

    public LossEntity execute(UpdateLossRecordDTO updateLossRecordDTO) {
        var loss = this.lossRepository.findById(updateLossRecordDTO.id()).orElseThrow(() -> {
            throw new EntityNotFoundException();
        });

        var lossEntity = new LossEntity();

        BeanUtils.copyProperties(loss, lossEntity);
        lossEntity.setValue(updateLossRecordDTO.value());

        return this.lossRepository.save(lossEntity);
    }
}
