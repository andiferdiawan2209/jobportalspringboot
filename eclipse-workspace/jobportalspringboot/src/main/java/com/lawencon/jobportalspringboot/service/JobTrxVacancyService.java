package com.lawencon.jobportalspringboot.service;

import java.util.List;

import com.lawencon.jobportalspringboot.model.request.CreateJobTrxVacancyRequest;
import com.lawencon.jobportalspringboot.model.request.CreateJobVacancyRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateJobTrxVacancyRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateJobVacancyRequest;
import com.lawencon.jobportalspringboot.model.response.File;
import com.lawencon.jobportalspringboot.model.response.JobVacancyResponse;
import com.lawencon.jobportalspringboot.model.response.JobVacancyTrxResponse;

import net.sf.jasperreports.engine.JRException;

public interface JobTrxVacancyService {
    void add(CreateJobTrxVacancyRequest request);
    List<JobVacancyTrxResponse> findAll();
    JobVacancyTrxResponse findById(String id);
    void edit(String id, UpdateJobTrxVacancyRequest request);

    void delete(String id);

    List<JobVacancyTrxResponse> findByRecruiterId();

    void updateStatusById(String jobTrxId, String statusId);

     File getReport() throws JRException;
}
