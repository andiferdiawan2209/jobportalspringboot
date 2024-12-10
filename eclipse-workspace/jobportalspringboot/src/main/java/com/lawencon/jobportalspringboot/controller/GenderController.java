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
import com.lawencon.jobportalspringboot.model.request.CreateGenderRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateGenderRequest;
import com.lawencon.jobportalspringboot.model.response.GenderResponse;
import com.lawencon.jobportalspringboot.model.response.WebResponse;
import com.lawencon.jobportalspringboot.service.GenderService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Gender", description="Gender API")
@RestController
@RequestMapping({ "/api/v1" })
@AllArgsConstructor
public class GenderController {

     private final GenderService genderService;

    @GetMapping(value = "/genders", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<GenderResponse>>> findAll() {
        return ResponseEntity.ok(ResponseHelper.ok(genderService.findAll()));
    }

    @PostMapping(value = "/genders", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<GenderResponse>> add(@Valid @RequestBody CreateGenderRequest request) {
        genderService.add(request);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    @PutMapping(value = "/genders/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<GenderResponse>> edit(@PathVariable String id, @Valid @RequestBody UpdateGenderRequest request) {
        genderService.edit(id, request);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    @GetMapping(value = "/genders/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<GenderResponse>> findById(@PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(genderService.findByid(id)));
    }

    @DeleteMapping(value = "/genders/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<GenderResponse>> deleteById(@PathVariable String id) {
        genderService.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok());
    }
}
