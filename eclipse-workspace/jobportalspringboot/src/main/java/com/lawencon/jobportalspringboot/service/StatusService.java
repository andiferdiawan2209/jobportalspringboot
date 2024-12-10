package com.lawencon.jobportalspringboot.service;

import java.util.List;

import com.lawencon.jobportalspringboot.model.request.CreateStatusRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateStatusRequest;
import com.lawencon.jobportalspringboot.model.response.StatusResponse;

public interface StatusService {
     void add(CreateStatusRequest request);

    void edit(String id, UpdateStatusRequest request);
    
    List<StatusResponse> findAll();

    void delete(String id);

    StatusResponse findByid(String id);
}
