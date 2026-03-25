package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.LoginRequestDTO;
import com.devsuperior.dscommerce.dto.TokenResponseDTO;
import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public TokenResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByEmailWithRoles(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        UserDetails principal = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new org.springframework.security.core.authority.SimpleGrantedAuthority(role.getAuthority())).toList()
        );

        String token = tokenService.generateToken(principal);
        return new TokenResponseDTO(token);
    }
}

