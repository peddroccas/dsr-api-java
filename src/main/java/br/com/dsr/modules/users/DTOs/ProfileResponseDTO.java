package br.com.dsr.modules.users.DTOs;

import java.util.UUID;

import br.com.dsr.modules.users.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private RoleEnum role;
}
