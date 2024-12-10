package com.lawencon.jobportalspringboot.service.impl;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportalspringboot.persistance.entity.OtpUser;
import com.lawencon.jobportalspringboot.persistance.entity.User;
import com.lawencon.jobportalspringboot.persistance.repository.OtpUserRepository;
import com.lawencon.jobportalspringboot.service.OtpService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OtpServiceImpl implements OtpService {
    
    private final OtpUserRepository otpUserRepository;

    @Override
    public void save(OtpUser otpUser) {
         otpUserRepository.saveAndFlush(otpUser);
    }

    @Override
    public Optional<OtpUser> findFirstByCode(String code) {
        return otpUserRepository.findByCode(code);
    }

    @Override
    public void delete(OtpUser otpUser) {
        otpUserRepository.delete(otpUser);
    }

    @Override
    public void deleteByUser(User user) {
        otpUserRepository.deleteByUser(user);
    }
    
}
