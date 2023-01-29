package com.spring.security.JWT.auth;

import com.spring.security.JWT.config.JwtService;
import com.spring.security.JWT.domain.Role;
import com.spring.security.JWT.domain.UserDetail;
import com.spring.security.JWT.domain.UserDetailRepository;
import com.spring.security.JWT.model.AuthenticationRequest;
import com.spring.security.JWT.model.AuthenticationResponse;
import com.spring.security.JWT.model.RegisterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserDetailRepository userDetailRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthenticationService(UserDetailRepository userDetailRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userDetailRepository = userDetailRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        UserDetail userDetail = new UserDetail();
        userDetail.setFirstName(registerRequest.getFirstname());
        userDetail.setLastName(registerRequest.getLastname());
        userDetail.setEmail(registerRequest.getEmail());
        userDetail.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userDetail.setRole(Role.USER);
        userDetailRepository.save(userDetail);
        String jwtToken = jwtService.generateToken(userDetail);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(jwtToken);
        return authenticationResponse;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        UserDetail userDetail = userDetailRepository.findByEmail(authenticationRequest.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(userDetail);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(jwtToken);
        return authenticationResponse;
    }
}
