package com.lawencon.jobportalspringboot.authentication.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.lawencon.jobportalspringboot.persistance.entity.User;

public interface JwtService {

    String generateToken(UserDetails userDetails, User user);

    String extractUsername(String token);

    boolean validateToken(String token, UserDetails userDetails);
}
