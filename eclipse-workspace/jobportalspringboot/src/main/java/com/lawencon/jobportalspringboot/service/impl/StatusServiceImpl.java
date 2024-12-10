package com.lawencon.jobportalspringboot.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportalspringboot.authentication.helper.SessionHelper;
import com.lawencon.jobportalspringboot.model.request.CreateStatusRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateStatusRequest;
import com.lawencon.jobportalspringboot.model.response.StatusResponse;
import com.lawencon.jobportalspringboot.persistance.entity.Status;
import com.lawencon.jobportalspringboot.persistance.repository.StatusRepository;
import com.lawencon.jobportalspringboot.service.StatusService;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class StatusServiceImpl implements StatusService {

    private StatusRepository repository;
     
    @Override
    public void add(CreateStatusRequest request) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can create");
        }
         Status item = new Status();
        item.setIsActive(true);
        item.setVersion(0l);
        BeanUtils.copyProperties(request, item);
        repository.saveAndFlush(item);
    }

    @Override
    public void edit(String id, UpdateStatusRequest request) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can edit");
        }
        Status item = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.valueOf(400), "Status Not Found"));
        item.setCode(request.getCode());
        item.setName(request.getName());
        item.setIsActive(true);
        item.setVersion(item.getVersion() + 1);
        repository.saveAndFlush(item);
    }

    @Override
    public List<StatusResponse> findAll() {
         List<Status> items = repository.findAll();
        List<StatusResponse> result = new ArrayList<>();
        for (Status item : items) {
            StatusResponse roleResponse = masToResponse(item);
            result.add(roleResponse);
        }
        return result;
    }

    @Override
    public void delete(String id) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can delete");
        }
        Optional<Status> statusOptional = repository.findById(id);

        if (statusOptional.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Status not found");
        }
    }

    @Override
    public StatusResponse findByid(String id) {
        Status item = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Status not found"));
        return masToResponse(item);
    }

    private StatusResponse masToResponse(Status item) {
        StatusResponse response = new StatusResponse();
        BeanUtils.copyProperties(item, response);
        return response;
    }
    
}
