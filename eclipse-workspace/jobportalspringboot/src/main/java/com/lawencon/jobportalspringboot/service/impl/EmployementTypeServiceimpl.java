package com.lawencon.jobportalspringboot.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportalspringboot.authentication.helper.SessionHelper;
import com.lawencon.jobportalspringboot.model.request.CreateEmployementTypeRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateEmployementTypeRequest;
import com.lawencon.jobportalspringboot.model.response.EmployementTypeResponse;
import com.lawencon.jobportalspringboot.persistance.entity.EmployementType;
import com.lawencon.jobportalspringboot.persistance.repository.EmployementTypeRepository;
import com.lawencon.jobportalspringboot.service.EmployementTypeService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployementTypeServiceimpl implements EmployementTypeService {

    private EmployementTypeRepository repository;

    @Override
    public void add(CreateEmployementTypeRequest request) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can create");
        }
        EmployementType item = new EmployementType();
        item.setIsActive(true);
        item.setVersion(0l);
        BeanUtils.copyProperties(request, item);
        repository.saveAndFlush(item);
    }

    @Override
    public void edit(String id, UpdateEmployementTypeRequest request) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can edit");
        }
        EmployementType item = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.valueOf(400), "EmployementType Not Found"));
        item.setCode(request.getCode());
        item.setName(request.getName());
        item.setIsActive(true);
        item.setVersion(item.getVersion() + 1);
        repository.saveAndFlush(item);
    }

    @Override
    public List<EmployementTypeResponse> findAll() {
        List<EmployementType> items = repository.findAll();
        List<EmployementTypeResponse> result = new ArrayList<>();
        for (EmployementType item : items) {
            EmployementTypeResponse roleResponse = masToResponse(item);
            result.add(roleResponse);
        }
        return result;
    }

    private EmployementTypeResponse masToResponse(EmployementType item) {
        EmployementTypeResponse response = new EmployementTypeResponse();
        BeanUtils.copyProperties(item, response);
        return response;
    }

    @Override
    public void delete(String id) {
        Optional<EmployementType> employementTypeOptional = repository.findById(id);

        if (employementTypeOptional.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"EmployementType not found");
        }
    }

    @Override
    public EmployementTypeResponse findByid(String id) {
       EmployementType item = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"EmployementType not found"));
        return masToResponse(item);
    }
}
