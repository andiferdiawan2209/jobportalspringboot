package com.lawencon.jobportalspringboot.service;

import java.util.List;

import com.lawencon.jobportalspringboot.model.request.CreateLocationRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateLocationRequest;
import com.lawencon.jobportalspringboot.model.response.LocationResponse;

public interface LocationService {
     void add(CreateLocationRequest request);

    void edit(String id, UpdateLocationRequest request);
    
    List<LocationResponse> findAll();

    void delete(String id);

     LocationResponse findByid(String id);
}
