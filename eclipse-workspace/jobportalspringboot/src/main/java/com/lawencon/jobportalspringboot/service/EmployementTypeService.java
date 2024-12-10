package com.lawencon.jobportalspringboot.service;

import java.util.List;

import com.lawencon.jobportalspringboot.model.request.CreateEmployementTypeRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateEmployementTypeRequest;
import com.lawencon.jobportalspringboot.model.response.EmployementTypeResponse;

public interface EmployementTypeService {

     void add(CreateEmployementTypeRequest request);

    void edit(String id, UpdateEmployementTypeRequest request);
    
    List<EmployementTypeResponse> findAll();

    void delete(String id);

    EmployementTypeResponse findByid(String id);
}
