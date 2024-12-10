package com.lawencon.jobportalspringboot.service;

import java.util.List;

import com.lawencon.jobportalspringboot.model.request.CreateGenderRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateGenderRequest;
import com.lawencon.jobportalspringboot.model.response.GenderResponse;
import com.lawencon.jobportalspringboot.persistance.entity.Gender;

public interface GenderService {

    void add(CreateGenderRequest request);

    void edit(String id, UpdateGenderRequest request);
    
    List<GenderResponse> findAll();

    void delete(String id);

    GenderResponse findByid(String id);

    Gender findEntityById(String id);
}
