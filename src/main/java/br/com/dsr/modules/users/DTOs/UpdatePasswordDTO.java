package br.com.dsr.modules.users.DTOs;

import org.hibernate.validator.constraints.Length;

public record UpdatePasswordDTO(@Length(min = 6) String password) {

}
