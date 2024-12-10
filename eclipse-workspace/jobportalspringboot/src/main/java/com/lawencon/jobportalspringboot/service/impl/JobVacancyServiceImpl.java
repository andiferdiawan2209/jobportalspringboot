package com.lawencon.jobportalspringboot.service.impl;


import com.lawencon.jobportalspringboot.service.JobVacancyService;
import com.lawencon.jobportalspringboot.model.request.CreateJobVacancyRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateJobVacancyRequest;
import com.lawencon.jobportalspringboot.model.response.JobVacancyResponse;
import com.lawencon.jobportalspringboot.persistance.entity.EmployementType;
import com.lawencon.jobportalspringboot.persistance.entity.ExperianceLevel;
import com.lawencon.jobportalspringboot.persistance.entity.JobTitle;
import com.lawencon.jobportalspringboot.persistance.entity.JobTitleDescription;
import com.lawencon.jobportalspringboot.persistance.entity.JobTitleSpesification;
import com.lawencon.jobportalspringboot.persistance.entity.JobVacancy;
import com.lawencon.jobportalspringboot.persistance.entity.Location;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportalspringboot.authentication.helper.SessionHelper;
import com.lawencon.jobportalspringboot.persistance.repository.EmployementTypeRepository;
import com.lawencon.jobportalspringboot.persistance.repository.ExperianceLevelRepository;
import com.lawencon.jobportalspringboot.persistance.repository.JobTitleRepository;
import com.lawencon.jobportalspringboot.persistance.repository.JobVacancyRepository;
import com.lawencon.jobportalspringboot.persistance.repository.LocationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JobVacancyServiceImpl implements JobVacancyService{

    private JobVacancyRepository jobVacancyRepository;
    
    private JobTitleRepository jobTitleRepository;
    
    private LocationRepository locationRepository;
    
    private EmployementTypeRepository employmentTypeRepository;
    
    private ExperianceLevelRepository experienceLevelRepository;

    @Override
    public void createJobVacancy(CreateJobVacancyRequest request) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can create job vacancy");
        }
        
        JobTitle jobTitle = jobTitleRepository.findById(request.getJobTitleId())
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job Title not found"));
        Location location = locationRepository.findById(request.getLocationId())
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.BAD_REQUEST, "Location not found"));
        EmployementType employmentType = employmentTypeRepository.findById(request.getEmploymentTypeId())
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employment Type not found"));
        ExperianceLevel experienceLevel = experienceLevelRepository.findById(request.getExperienceLevelId())
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.BAD_REQUEST, "Experience Level not found"));
        
        JobVacancy jobVacancy = new JobVacancy();
        jobVacancy.setJobTitle(jobTitle);
        jobVacancy.setLocation(location);
        jobVacancy.setEmployementType(employmentType);
        jobVacancy.setExperianceLevel(experienceLevel);
        jobVacancy.setSalaryMin(request.getSalaryMin());
        jobVacancy.setSalaryMax(request.getSalaryMax());
        jobVacancy.setJobOverview(request.getJobOverview());

        jobVacancyRepository.saveAndFlush(jobVacancy);
    }

    @Override
    public List<JobVacancyResponse> findAll() {
        return jobVacancyRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public JobVacancyResponse findById(String id) {
        JobVacancy jobVacancy = jobVacancyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job Vacancy not found"));
        return mapToResponse(jobVacancy);
    }

    @Override
    public void edit(String id, UpdateJobVacancyRequest request) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can create job vacancy");
        }
        JobVacancy jobVacancy = jobVacancyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job Vacancy not found"));
        
        jobVacancy.setSalaryMin(request.getSalaryMin());
        jobVacancy.setSalaryMax(request.getSalaryMax());
        jobVacancy.setJobOverview(request.getJobOverview());
        
        JobTitle jobTitle = jobTitleRepository.findById(request.getJobTitleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job Title not found"));
        jobVacancy.setJobTitle(jobTitle);
        
        Location location = locationRepository.findById(request.getLocationId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Location not found"));
        jobVacancy.setLocation(location);
        
        EmployementType employmentType = employmentTypeRepository.findById(request.getEmploymentTypeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employment Type not found"));
        jobVacancy.setEmployementType(employmentType);
        
        ExperianceLevel experienceLevel = experienceLevelRepository.findById(request.getExperienceLevelId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Experience Level not found"));
        jobVacancy.setExperianceLevel(experienceLevel);

        jobVacancyRepository.saveAndFlush(jobVacancy);
    }

    @Override
    public void delete(String id) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can create job vacancy");
        }
        if (!jobVacancyRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job Vacancy not found");
        }
        jobVacancyRepository.deleteById(id);
    }

    private JobVacancyResponse mapToResponse(JobVacancy jobVacancy) {
        JobVacancyResponse response = new JobVacancyResponse();
        response.setId(jobVacancy.getId());
        response.setJobTitle(jobVacancy.getJobTitle().getTitle());
        response.setLocation(jobVacancy.getLocation().getName());
        response.setEmploymentType(jobVacancy.getEmployementType().getName());
        response.setExperienceLevel(jobVacancy.getExperianceLevel().getName());
        response.setSalaryMin(jobVacancy.getSalaryMin());
        response.setSalaryMax(jobVacancy.getSalaryMax());
        response.setJobOverview(jobVacancy.getJobOverview());

        JobTitle jobTitle = jobVacancy.getJobTitle();
        response.setDescriptions(jobTitle.getDescriptions().stream()
                .map(JobTitleDescription::getDescription)
                .collect(Collectors.toList()));
        
        response.setSpecifications(jobTitle.getSpesifications().stream()
                .map(JobTitleSpesification::getDescription)
                .collect(Collectors.toList()));

        return response;
    }
} 
