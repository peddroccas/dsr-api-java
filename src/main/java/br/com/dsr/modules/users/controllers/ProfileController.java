package br.com.dsr.modules.users.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.dsr.modules.users.useCases.ProfileUseCase;

import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class ProfileController {

    @Autowired
    ProfileUseCase profileUseCase;

    @GetMapping("/profile")
    public ResponseEntity<Object> profile(HttpServletRequest request) {
        var id = request.getAttribute("id");
        try {
            var profile = profileUseCase.execute(UUID.fromString(id.toString()));
            return ResponseEntity.ok().body(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
