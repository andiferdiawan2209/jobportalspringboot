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
import com.lawencon.jobportalspringboot.model.request.CreateJobTrxVacancyRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateJobTrxVacancyRequest;
import com.lawencon.jobportalspringboot.model.response.File;
import com.lawencon.jobportalspringboot.model.response.JobVacancyTrxResponse;
import com.lawencon.jobportalspringboot.model.response.WebResponse;
import com.lawencon.jobportalspringboot.service.JobTrxVacancyService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "JobVacancyTrx", description="JobVacancyTrx API")
@RestController
@RequestMapping({ "/api/v1" })
@AllArgsConstructor
public class JobVacancyTrxController {

    private final JobTrxVacancyService jobVacancyTrxService;

    @PostMapping(value = "/jobvacancytrx", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<Void>> add(@Valid @RequestBody CreateJobTrxVacancyRequest request) {
        jobVacancyTrxService.add(request);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    
    @GetMapping(value = "/jobvacancytrx", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<JobVacancyTrxResponse>>> findAll() {
        return ResponseEntity.ok(ResponseHelper.ok(jobVacancyTrxService.findAll()));
    }

    @PutMapping(value = "/jobvacancytrx/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<Void>> edit(@PathVariable String id, @Valid @RequestBody UpdateJobTrxVacancyRequest request) {
        jobVacancyTrxService.edit(id, request);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    @GetMapping(value = "/jobvacancytrx/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<JobVacancyTrxResponse>> findById(@PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(jobVacancyTrxService.findById(id)));
    }

    @DeleteMapping(value = "/jobvacancytrx/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<JobVacancyTrxResponse>> deleteById(@PathVariable String id) {
        jobVacancyTrxService.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    @GetMapping(value = "/jobvacancytrx/report", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<File>> generateReport() throws Exception {
        return ResponseEntity.ok(ResponseHelper.ok(jobVacancyTrxService.getReport()));
    }
}
