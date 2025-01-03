package br.com.dsr.modules.users.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.dsr.modules.users.DTOs.UpdatePasswordDTO;
import br.com.dsr.modules.users.entities.UserEntity;
import br.com.dsr.modules.users.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UpdatePasswordUseCase {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity execute(UUID id, String password) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException();
                });

        var passwordEncoded = passwordEncoder.encode(password).toString();
        user.setPassword(passwordEncoded);

        return userRepository.save(user);
    }
}
