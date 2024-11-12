package br.com.dsr.modules.users.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dsr.exceptions.UserFoundException;
import br.com.dsr.modules.users.repositories.UserRepository;

@Service

public class HasAnyAdminUseCase {
    @Autowired
    private UserRepository userRepository;

    public void execute() {
        var hasAnyAdmin = this.userRepository.findByRole("ADMIN")
                .isEmpty();

        if (hasAnyAdmin) {
            throw new UserFoundException();
        }
    }
}
