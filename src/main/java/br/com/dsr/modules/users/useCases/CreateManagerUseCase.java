package br.com.dsr.modules.users.useCases;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.dsr.exceptions.EntityFoundException;
import br.com.dsr.modules.users.DTOs.CreateManagerRecordDTO;
import br.com.dsr.modules.users.entities.ManagerEntity;
import br.com.dsr.modules.users.enums.RoleEnum;
import br.com.dsr.modules.users.repositories.ManagerRepository;
import br.com.dsr.modules.users.repositories.UserRepository;

@Service
public class CreateManagerUseCase {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private UserRepository userRepository;

    public ManagerEntity execute(CreateManagerRecordDTO createManagerRecordDTO) {

        this.userRepository.findByEmail(createManagerRecordDTO.email()).ifPresent((user) -> {
            throw new EntityFoundException("User");
        });

        var managerEntity = new ManagerEntity();
        var password = passwordEncoder.encode("123456");
        var role = RoleEnum.MANAGER;

        BeanUtils.copyProperties(createManagerRecordDTO, managerEntity);
        managerEntity.setPassword(password);
        managerEntity.setRole(role);

        return this.managerRepository.save(managerEntity);
    }
}
