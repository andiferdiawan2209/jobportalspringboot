package com.lawencon.jobportalspringboot.service.impl;

import org.springframework.stereotype.Service;

import com.lawencon.jobportalspringboot.persistance.entity.Profile;
import com.lawencon.jobportalspringboot.persistance.repository.ProfileRepository;
import com.lawencon.jobportalspringboot.service.ProfileService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private ProfileRepository repository;
     
    @Override
    public void save(Profile profile) {
        repository.saveAndFlush(profile);
    }
    
}
