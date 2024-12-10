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
import com.lawencon.jobportalspringboot.model.request.CreateExperianceLevelRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateExperianceLevelRequest;
import com.lawencon.jobportalspringboot.model.response.ExperianceLevelResponse;
import com.lawencon.jobportalspringboot.model.response.WebResponse;
import com.lawencon.jobportalspringboot.service.ExperianceLevelService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@Tag(name = "Experiance", description="Experiance API")
@RestController
@RequestMapping({ "/api/v1" })
@AllArgsConstructor
public class ExperianceController {
     private final ExperianceLevelService experianceLevelService;

    @GetMapping(value = "/experiancelevel", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<ExperianceLevelResponse>>> findAll() {
        return ResponseEntity.ok(ResponseHelper.ok(experianceLevelService.findAll()));
    }

    @PostMapping(value = "/experiancelevel", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<ExperianceLevelResponse>> add(@Valid @RequestBody CreateExperianceLevelRequest request) {
        experianceLevelService.add(request);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    @PutMapping(value = "/experiancelevel/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<ExperianceLevelResponse>> edit(@PathVariable String id, @Valid @RequestBody UpdateExperianceLevelRequest request) {
        experianceLevelService.edit(id, request);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    @GetMapping(value = "/experiancelevel/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<ExperianceLevelResponse>> findById(@PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(experianceLevelService.findByid(id)));
    }

    @DeleteMapping(value = "/experiancelevel/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<ExperianceLevelResponse>> deleteById(@PathVariable String id) {
        experianceLevelService.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok());
    }
}
