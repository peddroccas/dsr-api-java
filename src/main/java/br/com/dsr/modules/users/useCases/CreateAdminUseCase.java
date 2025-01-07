package br.com.dsr.modules.users.useCases;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.dsr.exceptions.EntityFoundException;
import br.com.dsr.modules.users.DTOs.AdminRecordDTO;
import br.com.dsr.modules.users.entities.AdminEntity;
import br.com.dsr.modules.users.enums.RoleEnum;
import br.com.dsr.modules.users.repositories.AdminRepository;

@Service
public class CreateAdminUseCase {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminRepository adminRepository;

    public AdminEntity execute(AdminRecordDTO adminRecordDTO) {
        this.adminRepository
                .findByEmail(adminRecordDTO.email())
                .ifPresent((user) -> {
                    throw new EntityFoundException("Usuário");
                });

        var adminEntity = new AdminEntity();
        var password = passwordEncoder.encode("123456");
        var role = RoleEnum.ADMIN;

        BeanUtils.copyProperties(adminRecordDTO, adminEntity);
        adminEntity.setPassword(password);
        adminEntity.setRole(role);

        return this.adminRepository.save(adminEntity);
    }
}
