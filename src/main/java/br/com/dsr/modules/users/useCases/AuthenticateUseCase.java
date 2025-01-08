package br.com.dsr.modules.users.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.dsr.modules.users.DTOs.AuthRecordDTO;
import br.com.dsr.modules.users.DTOs.AuthResponseDTO;
import br.com.dsr.modules.users.repositories.UserRepository;

@Service
public class AuthenticateUseCase {
    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponseDTO execute(AuthRecordDTO authRecordDTO)
            throws AuthenticationException {
        var user = this.userRepository.findByEmail(authRecordDTO.email())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("email/password incorrect");
                });

        var passwordMatches = this.passwordEncoder.matches(authRecordDTO.password(), user.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofMinutes(120));
        var token = JWT.create()
                .withExpiresAt(expiresIn)
                .withSubject(user.getId().toString())
                .withClaim("roles", Arrays.asList(user.getRole().toString()))
                .sign(algorithm);

        var authResponseDTO = AuthResponseDTO.builder()
                .access_token(token)
                .build();

        return authResponseDTO;
    }
}
