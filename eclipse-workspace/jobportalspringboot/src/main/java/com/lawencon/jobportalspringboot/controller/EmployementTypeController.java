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
import com.lawencon.jobportalspringboot.model.request.CreateEmployementTypeRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateEmployementTypeRequest;
import com.lawencon.jobportalspringboot.model.response.EmployementTypeResponse;
import com.lawencon.jobportalspringboot.model.response.WebResponse;
import com.lawencon.jobportalspringboot.service.EmployementTypeService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "EmployementType", description="EmployementType API")
@RestController
@RequestMapping({ "/api/v1" })
@AllArgsConstructor
public class EmployementTypeController {
     private final EmployementTypeService employementTypeService;

    @GetMapping(value = "/employementtype", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<EmployementTypeResponse>>> findAll() {
        return ResponseEntity.ok(ResponseHelper.ok(employementTypeService.findAll()));
    }

    @PostMapping(value = "/employementtype", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<EmployementTypeResponse>> add(@Valid @RequestBody CreateEmployementTypeRequest request) {
        employementTypeService.add(request);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    @PutMapping(value = "/employementtype/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<EmployementTypeResponse>> edit(@PathVariable String id, @Valid @RequestBody UpdateEmployementTypeRequest request) {
        employementTypeService.edit(id, request);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    @GetMapping(value = "/employementtype/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<EmployementTypeResponse>> findById(@PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(employementTypeService.findByid(id)));
    }

    @DeleteMapping(value = "/employementtype/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<EmployementTypeResponse>> deleteById(@PathVariable String id) {
        employementTypeService.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok());
    }
}
