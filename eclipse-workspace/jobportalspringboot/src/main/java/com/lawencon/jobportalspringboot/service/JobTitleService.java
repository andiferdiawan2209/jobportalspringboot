package com.lawencon.jobportalspringboot.service;

import java.util.List;

import com.lawencon.jobportalspringboot.model.request.CreateJobTitleRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateJobTitleRequest;
import com.lawencon.jobportalspringboot.model.response.JobTitleResponse;

public interface JobTitleService {
    void save(CreateJobTitleRequest request);

    void edit(String id, UpdateJobTitleRequest request);
    
    List<JobTitleResponse> findAll();

    void delete(String id);

    JobTitleResponse findByid(String id);

}
