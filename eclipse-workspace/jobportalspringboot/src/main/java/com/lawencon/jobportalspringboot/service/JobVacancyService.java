package com.lawencon.jobportalspringboot.service;

import java.util.List;

import com.lawencon.jobportalspringboot.model.request.CreateJobVacancyRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateJobVacancyRequest;
import com.lawencon.jobportalspringboot.model.response.JobVacancyResponse;

public interface JobVacancyService {
    void createJobVacancy(CreateJobVacancyRequest request);
     List<JobVacancyResponse> findAll();
    JobVacancyResponse findById(String id);
    void edit(String id, UpdateJobVacancyRequest request);
    void delete(String id);
}
