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
import com.lawencon.jobportalspringboot.model.request.CreateStatusRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateStatusRequest;
import com.lawencon.jobportalspringboot.model.response.StatusResponse;
import com.lawencon.jobportalspringboot.model.response.WebResponse;
import com.lawencon.jobportalspringboot.service.StatusService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Status", description="Status API")
@RestController
@RequestMapping({ "/api/v1" })
@AllArgsConstructor
public class StatusController {
      private final StatusService statusService;

    @GetMapping(value = "/status", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<StatusResponse>>> findAll() {
        return ResponseEntity.ok(ResponseHelper.ok(statusService.findAll()));
    }

    @PostMapping(value = "/status", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<StatusResponse>> add(@Valid @RequestBody CreateStatusRequest request) {
        statusService.add(request);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    @PutMapping(value = "/status/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<StatusResponse>> edit(@PathVariable String id, @Valid @RequestBody UpdateStatusRequest request) {
        statusService.edit(id, request);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    @GetMapping(value = "/status/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<StatusResponse>> findById(@PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(statusService.findByid(id)));
    }

    @DeleteMapping(value = "/status/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<StatusResponse>> deleteById(@PathVariable String id) {
        statusService.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok());
    }
}
