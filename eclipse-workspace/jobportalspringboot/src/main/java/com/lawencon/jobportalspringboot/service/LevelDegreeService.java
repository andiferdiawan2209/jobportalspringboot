package com.lawencon.jobportalspringboot.service;

import java.util.List;

import com.lawencon.jobportalspringboot.model.request.CreateLevelDegreeRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateLevelDegreeRequest;
import com.lawencon.jobportalspringboot.model.response.LevelDegreeResponse;

public interface LevelDegreeService {
     void add(CreateLevelDegreeRequest request);

    void edit(String id, UpdateLevelDegreeRequest request);
    
    List<LevelDegreeResponse> findAll();

    void delete(String id);

    LevelDegreeResponse findByid(String id);
}
