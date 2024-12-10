package com.lawencon.jobportalspringboot.service;

import java.util.Optional;

import com.lawencon.jobportalspringboot.persistance.entity.OtpUser;
import com.lawencon.jobportalspringboot.persistance.entity.Profile;
import com.lawencon.jobportalspringboot.persistance.entity.User;

public interface OtpService {
    void save(OtpUser otpUser);

    void delete(OtpUser otpUser);
    
    void deleteByUser(User user);
     
    Optional<OtpUser> findFirstByCode(String code);
}

