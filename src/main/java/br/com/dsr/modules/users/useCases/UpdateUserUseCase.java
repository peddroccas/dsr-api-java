package br.com.dsr.modules.users.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dsr.modules.users.DTOs.UserRecordDTO;
import br.com.dsr.modules.users.entities.UserEntity;
import br.com.dsr.modules.users.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UpdateUserUseCase {
    @Autowired
    private UserRepository userRepository;

    public UserEntity execute(UserRecordDTO userRecordDTO) {
        var user = userRepository.findById(userRecordDTO.id())
                .orElseThrow(() -> {
                    throw new EntityNotFoundException();
                });

        return userRepository.save(user);
    }
}
