package br.com.dsr.modules.users.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dsr.exceptions.UserFoundException;
import br.com.dsr.modules.users.repositories.AdminRepository;

@Service
public class HasAnyAdminUseCase {
    @Autowired
    private AdminRepository adminRepository;

    public void execute() {
        var hasAnyAdmin = !this.adminRepository.findAll()
                .isEmpty();

        if (hasAnyAdmin) {
            throw new UserFoundException();
        }
    }
}
