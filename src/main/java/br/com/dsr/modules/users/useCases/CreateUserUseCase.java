package br.com.dsr.modules.users.useCases;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.dsr.exceptions.UserFoundException;
import br.com.dsr.modules.users.DTOs.UserRecordDTO;
import br.com.dsr.modules.users.entities.UserEntity;
import br.com.dsr.modules.users.repositories.UserRepository;

@Service
public class CreateUserUseCase {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public UserEntity execute(UserRecordDTO userRecordDTO) {
        this.userRepository
                .findByEmail(userRecordDTO.email())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        var userEntity = new UserEntity();
        var password = passwordEncoder.encode("123456");

        BeanUtils.copyProperties(userRecordDTO, userEntity);
        userEntity.setPassword(password);
        userEntity.setRole(userRecordDTO.role());

        return this.userRepository.save(userEntity);
    }
}
