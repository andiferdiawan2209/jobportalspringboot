package com.lawencon.jobportalspringboot.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportalspringboot.authentication.service.AuthenticationService;
import com.lawencon.jobportalspringboot.helper.ResponseHelper;
import com.lawencon.jobportalspringboot.model.request.CreateCandidateRequest;
import com.lawencon.jobportalspringboot.model.request.LoginRequest;
import com.lawencon.jobportalspringboot.model.request.ResendOtpRequest;
import com.lawencon.jobportalspringboot.model.request.VerifyRequest;
import com.lawencon.jobportalspringboot.model.response.JwtAuthenticationResponse;
import com.lawencon.jobportalspringboot.model.response.WebResponse;
import com.lawencon.jobportalspringboot.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@Tag(name = "Auth", description="Auth API")
@RestController
@RequestMapping({ "/api/v1" })
@AllArgsConstructor
public class AuthenticationController {
    
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<JwtAuthenticationResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(ResponseHelper.ok(authenticationService.login(request)));
    }


    @PostMapping(value = "/register", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<Void>> createCandidate(@Valid @RequestBody CreateCandidateRequest request) {
        userService.createCandidate(request);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    @PostMapping(value = "/verify", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<Void>> verify(@Valid @RequestBody VerifyRequest request) {
        userService.verify(request.getCode());
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    @PostMapping(value = "/resend-otp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<Void>> resendOtp(@Valid @RequestBody ResendOtpRequest request) {
        userService.resendOtp(request.getEmail());
        return ResponseEntity.ok(ResponseHelper.ok());
    }
    
}
