package br.com.dsr.modules.financial.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dsr.modules.financial.repositories.LossRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DeleteLossUseCase {
    @Autowired
    private LossRepository lossRepository;

    public void execute(UUID id) {
        this.lossRepository.findById(id)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException();
                });

        this.lossRepository.deleteById(id);
    }
}
