package br.com.dsr.modules.users.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dsr.modules.users.DTOs.ProfileResponseDTO;
import br.com.dsr.modules.users.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfileUseCase {
    @Autowired
    UserRepository userRepository;

    public ProfileResponseDTO execute(UUID id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException();
                });

        var userDTO = ProfileResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .build();

        return userDTO;
    }
}
