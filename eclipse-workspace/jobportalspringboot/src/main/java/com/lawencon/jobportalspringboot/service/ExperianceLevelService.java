package com.lawencon.jobportalspringboot.service;

import java.util.List;

import com.lawencon.jobportalspringboot.model.request.CreateExperianceLevelRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateExperianceLevelRequest;
import com.lawencon.jobportalspringboot.model.response.ExperianceLevelResponse;

public interface ExperianceLevelService {
     void add(CreateExperianceLevelRequest request);

    void edit(String id, UpdateExperianceLevelRequest request);
    
    List<ExperianceLevelResponse> findAll();

    void delete(String id);

    ExperianceLevelResponse findByid(String id);
}
