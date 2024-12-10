package com.lawencon.jobportalspringboot.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportalspringboot.authentication.helper.SessionHelper;
import com.lawencon.jobportalspringboot.model.request.CreateJobTitleRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateJobTitleRequest;
import com.lawencon.jobportalspringboot.model.response.JobTitleResponse;
import com.lawencon.jobportalspringboot.persistance.entity.JobTitle;
import com.lawencon.jobportalspringboot.persistance.entity.JobTitleDescription;
import com.lawencon.jobportalspringboot.persistance.entity.JobTitleSpesification;
import com.lawencon.jobportalspringboot.persistance.repository.JobTitleDescriptionRepository;
import com.lawencon.jobportalspringboot.persistance.repository.JobTitleRepository;
import com.lawencon.jobportalspringboot.persistance.repository.JobTitleSpesificationRepository;
import com.lawencon.jobportalspringboot.service.JobTitleService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JobTitleServiceImpl implements JobTitleService {

    private JobTitleRepository jobTitleRepository;


    private JobTitleDescriptionRepository jobTitleDescriptionRepository;


    private JobTitleSpesificationRepository jobTitleSpesificationRepository;
    
    @Override
    public void save(CreateJobTitleRequest request) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can create");
        }
        JobTitle jobTitle = new JobTitle();
        jobTitle.setTitle(request.getTitle());
        jobTitle.setIsActive(true);

        List<JobTitleDescription> descriptions = request.getDescriptions().stream()
                .map(desc -> new JobTitleDescription(jobTitle, desc))
                .collect(Collectors.toList());

        List<JobTitleSpesification> spesifications = request.getSpesifications().stream()
                .map(spec -> new JobTitleSpesification(jobTitle, spec))
                .collect(Collectors.toList());

        jobTitle.setDescriptions(descriptions);
        jobTitle.setSpesifications(spesifications);

        jobTitleRepository.saveAndFlush(jobTitle);
    }
    
    @Override
    @Transactional
    public void edit(String id, UpdateJobTitleRequest request) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can edit");
        }
        JobTitle jobTitle = jobTitleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"JobTitle with ID " + id + " not found"));

        jobTitle.setTitle(request.getTitle());
        jobTitle.setIsActive(request.getIsActive());

        jobTitle.getDescriptions().clear();
        request.getDescriptions().forEach(desc -> {
            jobTitle.getDescriptions().add(new JobTitleDescription(jobTitle, desc));
        });

        jobTitle.getSpesifications().clear();
        request.getSpesifications().forEach(spec -> {
            jobTitle.getSpesifications().add(new JobTitleSpesification(jobTitle, spec));
        });

        jobTitleRepository.saveAndFlush(jobTitle);

    }
    
    @Override
    public List<JobTitleResponse> findAll() {
        return jobTitleRepository.findAll().stream()
                .map(JobTitleResponse::new)
                .collect(Collectors.toList());
    }
    @Override
    public void delete(String id) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can delete");
        }
        JobTitle jobTitle = jobTitleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"JobTitle with ID " + id + " not found"));
        jobTitleRepository.delete(jobTitle);
    }
    @Override
    public JobTitleResponse findByid(String id) {
        JobTitle jobTitle = jobTitleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"JobTitle with ID " + id + " not found"));
        return new JobTitleResponse(jobTitle);
    }
    
}
