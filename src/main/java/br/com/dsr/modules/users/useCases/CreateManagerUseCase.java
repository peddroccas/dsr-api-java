package br.com.dsr.modules.users.useCases;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.dsr.exceptions.EntityFoundException;
import br.com.dsr.modules.users.DTOs.ManagerRecordDTO;
import br.com.dsr.modules.users.entities.ManagerEntity;
import br.com.dsr.modules.users.enums.RoleEnum;
import br.com.dsr.modules.users.repositories.ManagerRepository;

@Service
public class CreateManagerUseCase {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ManagerRepository managerRepository;

    public ManagerEntity execute(ManagerRecordDTO managerRecordDTO) {
        this.managerRepository.findByEmail(managerRecordDTO.email()).ifPresent((user) -> {
            throw new EntityFoundException("Gerente");
        });

        var managerEntity = new ManagerEntity();
        var password = passwordEncoder.encode("123456");
        var role = RoleEnum.MANAGER;

        BeanUtils.copyProperties(managerRecordDTO, managerEntity);
        managerEntity.setPassword(password);
        managerEntity.setRole(role);

        return this.managerRepository.save(managerEntity);
    }
}
