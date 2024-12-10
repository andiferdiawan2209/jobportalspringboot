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
import com.lawencon.jobportalspringboot.model.request.CreateJobTitleRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateJobTitleRequest;
import com.lawencon.jobportalspringboot.model.response.JobTitleResponse;
// import com.lawencon.jobportalspringboot.model.request.UpdateJobTitleRequest;
// import com.lawencon.jobportalspringboot.model.response.JobTitleResponse;
import com.lawencon.jobportalspringboot.model.response.WebResponse;
import com.lawencon.jobportalspringboot.service.JobTitleService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@Tag(name = "JobTitle", description="JobTitle API")
@RestController
@RequestMapping({ "/api/v1" })
@AllArgsConstructor
public class JobTitleController {
    private final JobTitleService jobTitleService;

    @GetMapping(value = "/jobtitle", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<JobTitleResponse>>> findAll() {
        return ResponseEntity.ok(ResponseHelper.ok(jobTitleService.findAll()));
    }

    @PostMapping(value = "/jobtitle", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<JobTitleResponse>> save(@Valid @RequestBody CreateJobTitleRequest request) {
        jobTitleService.save(request);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    @PutMapping(value = "/jobtitle/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<JobTitleResponse>> edit(@PathVariable String id, @Valid @RequestBody UpdateJobTitleRequest request) {
        jobTitleService.edit(id, request);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    @GetMapping(value = "/jobtitle/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<JobTitleResponse>> findById(@PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(jobTitleService.findByid(id)));
    }

    @DeleteMapping(value = "/jobtitle/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<JobTitleResponse>> deleteById(@PathVariable String id) {
        jobTitleService.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok());
    }
}
