package br.com.dsr.modules.users.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dsr.modules.users.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DeleteUserUseCase {
    @Autowired
    private UserRepository userRepository;

    public Object execute(UUID id) {
        this.userRepository.findById(id)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException();
                });

        return this.userRepository.deleteById(id);
    }
}
