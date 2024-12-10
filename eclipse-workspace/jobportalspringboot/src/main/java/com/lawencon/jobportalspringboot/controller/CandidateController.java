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
import com.lawencon.jobportalspringboot.model.response.CandidateNotifResponse;
import com.lawencon.jobportalspringboot.model.response.WebResponse;
import com.lawencon.jobportalspringboot.service.CandidateService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Candidate", description="Candidate API")
@RestController
@RequestMapping({ "/api/v1" })
@AllArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;
    
     @GetMapping(value = "/candidate/notifications", produces=MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<WebResponse<List<CandidateNotifResponse>>> findAllNotif() {
         return ResponseEntity.ok(ResponseHelper.ok(candidateService.findAllNotif()));
     }
}
