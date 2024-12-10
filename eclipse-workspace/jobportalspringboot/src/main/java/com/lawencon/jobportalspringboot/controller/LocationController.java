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
import com.lawencon.jobportalspringboot.model.request.CreateLocationRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateLocationRequest;
import com.lawencon.jobportalspringboot.model.response.LocationResponse;
import com.lawencon.jobportalspringboot.model.response.WebResponse;
import com.lawencon.jobportalspringboot.service.LocationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Location", description="Location API")
@RestController
@RequestMapping({ "/api/v1" })
@AllArgsConstructor
public class LocationController {
     private final LocationService locationService;

    @GetMapping(value = "/location", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<LocationResponse>>> findAll() {
        return ResponseEntity.ok(ResponseHelper.ok(locationService.findAll()));
    }

    @PostMapping(value = "/location", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<LocationResponse>> add(@Valid @RequestBody CreateLocationRequest request) {
        locationService.add(request);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    @PutMapping(value = "/location/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<LocationResponse>> edit(@PathVariable String id, @Valid @RequestBody UpdateLocationRequest request) {
        locationService.edit(id, request);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    @GetMapping(value = "/location/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<LocationResponse>> findById(@PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(locationService.findByid(id)));
    }

    @DeleteMapping(value = "/location/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<LocationResponse>> deleteById(@PathVariable String id) {
        locationService.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok());
    }
}
