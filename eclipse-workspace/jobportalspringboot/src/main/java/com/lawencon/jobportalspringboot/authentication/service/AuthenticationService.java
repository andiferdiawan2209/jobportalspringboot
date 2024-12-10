package com.lawencon.jobportalspringboot.authentication.service;

import com.lawencon.jobportalspringboot.model.request.LoginRequest;
import com.lawencon.jobportalspringboot.model.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse login(LoginRequest loginRequest);
}
