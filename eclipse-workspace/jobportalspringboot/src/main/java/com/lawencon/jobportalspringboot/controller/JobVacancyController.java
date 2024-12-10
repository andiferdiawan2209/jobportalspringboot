package com.lawencon.jobportalspringboot.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportalspringboot.helper.ResponseHelper;
import com.lawencon.jobportalspringboot.model.request.CreateJobVacancyRequest;
import com.lawencon.jobportalspringboot.model.request.CreateJobTrxVacancyRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateJobVacancyRequest;
import com.lawencon.jobportalspringboot.model.response.JobVacancyResponse;
import com.lawencon.jobportalspringboot.model.response.WebResponse;
import com.lawencon.jobportalspringboot.service.JobVacancyService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "JobVacancy", description="JobVacancy API")
@RestController
@RequestMapping({ "/api/v1" })
@AllArgsConstructor
public class JobVacancyController {
    private final JobVacancyService jobVacancyService;


    @PostMapping(value = "/jobvacancy", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<Void>> add(@Valid @RequestBody CreateJobVacancyRequest request) {
        jobVacancyService.createJobVacancy(request);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    
    @GetMapping(value = "/jobvacancy", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<JobVacancyResponse>>> findAll() {
        return ResponseEntity.ok(ResponseHelper.ok(jobVacancyService.findAll()));
    }

    @PutMapping(value = "/jobvacancy/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<Void>> edit(@PathVariable String id, @Valid @RequestBody UpdateJobVacancyRequest request) {
        jobVacancyService.edit(id, request);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    @GetMapping(value = "/jobvacancy/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<JobVacancyResponse>> findById(@PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(jobVacancyService.findById(id)));
    }

    @DeleteMapping(value = "/jobvacancy/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<JobVacancyResponse>> deleteById(@PathVariable String id) {
        jobVacancyService.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok());
    }


}
