package com.lawencon.jobportalspringboot.service;

import java.util.List;

import com.lawencon.jobportalspringboot.model.request.UpdateStatusVacancy;
import com.lawencon.jobportalspringboot.model.response.JobVacancyResponseByReqruiter;
import com.lawencon.jobportalspringboot.model.response.JobVacancyTrxResponse;

public interface RecruiterService {
    List<JobVacancyTrxResponse> findAllVacancy();

    void updateStatusVacancy(String id, UpdateStatusVacancy request);
}
