package br.com.dsr.modules.users.useCases;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dsr.exceptions.EntityFoundException;
import br.com.dsr.modules.users.DTOs.UpdateManagerRecordDTO;
import br.com.dsr.modules.users.entities.ManagerEntity;
import br.com.dsr.modules.users.repositories.ManagerRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UpdateManagerUseCase {
    @Autowired
    private ManagerRepository managerRepository;

    public ManagerEntity execute(UpdateManagerRecordDTO updateManagerRecordDTO) {
        var manager = this.managerRepository.findById(updateManagerRecordDTO.id()).orElseThrow(() -> {
            throw new EntityNotFoundException();
        });
        this.managerRepository.findByEmail(updateManagerRecordDTO.email()).ifPresent((managerWithEmail) -> {
            Boolean sameManager = managerWithEmail.getId() == manager.getId();
            if (!sameManager) {
                throw new EntityFoundException("Manager");
            }

        });

        var managerEntity = new ManagerEntity();

        BeanUtils.copyProperties(updateManagerRecordDTO, managerEntity);
        managerEntity.setPassword(manager.getPassword());
        managerEntity.setRole(manager.getRole());

        return this.managerRepository.save(managerEntity);

    }

}
