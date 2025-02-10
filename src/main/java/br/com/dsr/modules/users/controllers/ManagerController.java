package br.com.dsr.modules.users.controllers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import br.com.dsr.modules.users.DTOs.CreateManagerRecordDTO;
import br.com.dsr.modules.users.DTOs.ManagerResponseDTO;
import br.com.dsr.modules.users.DTOs.UpdateManagerRecordDTO;
import br.com.dsr.modules.users.useCases.CreateManagerUseCase;
import br.com.dsr.modules.users.useCases.FindAllManagersUseCase;
import br.com.dsr.modules.users.useCases.UpdateManagerUseCase;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/managers")
public class ManagerController {

    @Autowired
    private CreateManagerUseCase createManagerUseCase;
    @Autowired
    private UpdateManagerUseCase updateManagerUseCase;
    @Autowired
    private FindAllManagersUseCase findAllManagersUseCase;

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> create(@RequestBody @Valid CreateManagerRecordDTO createManagerRecordDTO) {
        try {
            this.createManagerUseCase.execute(createManagerRecordDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> update(@RequestBody @Valid UpdateManagerRecordDTO updateManagerRecordDTO) {
        try {
            this.updateManagerUseCase.execute(updateManagerRecordDTO);
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> findAll() {
        try {
            var managers = this.findAllManagersUseCase.execute();

            var managersDTO = managers.stream()
                    .map(manager -> ManagerResponseDTO.builder()
                            .id(manager.getId())
                            .email(manager.getEmail())
                            .name(manager.getName())
                            .storeId(manager.getStoreId())
                            .build())
                    .collect(Collectors.toList());

            return ResponseEntity.ok().body(managersDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
