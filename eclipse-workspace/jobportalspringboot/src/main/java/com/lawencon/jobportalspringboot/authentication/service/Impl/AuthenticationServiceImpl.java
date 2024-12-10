package com.lawencon.jobportalspringboot.authentication.service.Impl;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportalspringboot.authentication.service.AuthenticationService;
import com.lawencon.jobportalspringboot.authentication.service.JwtService;
import com.lawencon.jobportalspringboot.model.request.LoginRequest;
import com.lawencon.jobportalspringboot.model.response.JwtAuthenticationResponse;
import com.lawencon.jobportalspringboot.persistance.entity.User;
import com.lawencon.jobportalspringboot.service.UserService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    
    @Override
    @Transactional
    public JwtAuthenticationResponse login(LoginRequest loginRequest) {
        Optional<User> userOpt = userService.getUserByUsernameAndPassword(loginRequest.getUsername(),
                loginRequest.getPassword());
        
        if (!userOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong username or password!");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));

        var userPrinciple = userService.userDetailsService().loadUserByUsername(loginRequest.getUsername());
        User user = userOpt.get();
        String token = jwtService.generateToken(userPrinciple, user);
        return JwtAuthenticationResponse.builder().token(token).build();
    }

    
}
