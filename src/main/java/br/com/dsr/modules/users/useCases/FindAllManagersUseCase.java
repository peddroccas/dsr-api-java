package br.com.dsr.modules.users.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dsr.modules.users.entities.ManagerEntity;
import br.com.dsr.modules.users.repositories.ManagerRepository;

@Service
public class FindAllManagersUseCase {
    @Autowired
    private ManagerRepository managerRepository;

    public List<ManagerEntity> execute() {
        var managers = this.managerRepository.findAll();

        return managers;
    }
}
