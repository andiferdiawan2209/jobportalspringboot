package com.lawencon.jobportalspringboot.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportalspringboot.authentication.helper.SessionHelper;
import com.lawencon.jobportalspringboot.model.request.CreateLocationRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateLocationRequest;
import com.lawencon.jobportalspringboot.model.response.LocationResponse;
import com.lawencon.jobportalspringboot.persistance.entity.Location;
import com.lawencon.jobportalspringboot.persistance.repository.LocationRepository;
import com.lawencon.jobportalspringboot.service.LocationService;

import lombok.AllArgsConstructor;



@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService{
    private LocationRepository repository;

    @Override
    public void add(CreateLocationRequest request) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can create");
        }
        Location item = new Location();
        item.setIsActive(true);
        item.setVersion(0l);
        BeanUtils.copyProperties(request, item);
        repository.saveAndFlush(item);
    }

    @Override
    public void edit(String id, UpdateLocationRequest request) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can edit");
        }
        Location item = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.valueOf(400), "Location Not Found"));
        item.setCode(request.getCode());
        item.setName(request.getName());
        item.setIsActive(true);
        item.setVersion(item.getVersion() + 1);
        repository.saveAndFlush(item);
    }

    @Override
    public List<LocationResponse> findAll() {
        List<Location> items = repository.findAll();
        List<LocationResponse> result = new ArrayList<>();
        for (Location item : items) {
            LocationResponse roleResponse = masToResponse(item);
            result.add(roleResponse);
        }
        return result;
    }

    private LocationResponse masToResponse(Location item) {
        LocationResponse response = new LocationResponse();
        BeanUtils.copyProperties(item, response);
        return response;
    }

    @Override
    public void delete(String id) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can delete");
        }
        Optional<Location> locationOptional = repository.findById(id);

        if (locationOptional.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Location not found");
        }
    }

    @Override
    public LocationResponse findByid(String id) {
       Location item = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Location not found"));
        return masToResponse(item);
    }
}
