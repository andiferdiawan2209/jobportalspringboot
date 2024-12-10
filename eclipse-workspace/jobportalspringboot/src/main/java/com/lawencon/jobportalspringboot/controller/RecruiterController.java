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
import com.lawencon.jobportalspringboot.model.request.UpdateStatusVacancy;
import com.lawencon.jobportalspringboot.model.response.JobVacancyTrxResponse;
import com.lawencon.jobportalspringboot.model.response.WebResponse;
import com.lawencon.jobportalspringboot.service.RecruiterService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Recruiter", description="Recruiter API")
@RestController
@RequestMapping({ "/api/v1" })
@AllArgsConstructor
public class RecruiterController {
    
    private final RecruiterService recruiterService;
    
     @GetMapping(value = "/recruiter/jobvacancy", produces=MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<WebResponse<List<JobVacancyTrxResponse>>> findAllVacancy() {
         return ResponseEntity.ok(ResponseHelper.ok(recruiterService.findAllVacancy()));
     }
    
    @PutMapping(value = "/recruiter/jobtrxvacancy/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<Void>> updateStatusVacancy(@PathVariable String id, @Valid @RequestBody UpdateStatusVacancy request) {
        recruiterService.updateStatusVacancy(id, request);
        return ResponseEntity.ok(ResponseHelper.ok());
    }
}
