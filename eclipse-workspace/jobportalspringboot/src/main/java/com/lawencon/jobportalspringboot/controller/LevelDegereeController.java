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
import com.lawencon.jobportalspringboot.model.request.CreateLevelDegreeRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateLevelDegreeRequest;
import com.lawencon.jobportalspringboot.model.response.LevelDegreeResponse;
import com.lawencon.jobportalspringboot.model.response.WebResponse;
import com.lawencon.jobportalspringboot.service.LevelDegreeService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "LevelDegree", description="LevelDegree API")
@RestController
@RequestMapping({ "/api/v1" })
@AllArgsConstructor
public class LevelDegereeController {
     private final LevelDegreeService levelDegreeService;

    @GetMapping(value = "/leveldegree", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<LevelDegreeResponse>>> findAll() {
        return ResponseEntity.ok(ResponseHelper.ok(levelDegreeService.findAll()));
    }

    @PostMapping(value = "/leveldegree", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<LevelDegreeResponse>> add(@Valid @RequestBody CreateLevelDegreeRequest request) {
        levelDegreeService.add(request);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    @PutMapping(value = "/leveldegree/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<LevelDegreeResponse>> edit(@PathVariable String id, @Valid @RequestBody UpdateLevelDegreeRequest request) {
        levelDegreeService.edit(id, request);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    @GetMapping(value = "/leveldegree/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<LevelDegreeResponse>> findById(@PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(levelDegreeService.findByid(id)));
    }

    @DeleteMapping(value = "/leveldegree/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<LevelDegreeResponse>> deleteById(@PathVariable String id) {
        levelDegreeService.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok());
    }
}
