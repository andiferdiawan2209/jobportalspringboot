package com.lawencon.jobportalspringboot.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.jobportalspringboot.model.request.UpdateStatusVacancy;
import com.lawencon.jobportalspringboot.model.response.JobVacancyResponseByReqruiter;
import com.lawencon.jobportalspringboot.model.response.JobVacancyTrxResponse;
import com.lawencon.jobportalspringboot.service.JobTrxVacancyService;
import com.lawencon.jobportalspringboot.service.RecruiterService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RecruiterServiceImpl implements RecruiterService {

    private JobTrxVacancyService jobTrxVacancyService;
    @Override
    public List<JobVacancyTrxResponse> findAllVacancy() {
        return jobTrxVacancyService.findByRecruiterId();
    }

    @Override
    public void updateStatusVacancy(String id, UpdateStatusVacancy request) {
        jobTrxVacancyService.updateStatusById(id, request.getStatusId());
    };
    
    
}
