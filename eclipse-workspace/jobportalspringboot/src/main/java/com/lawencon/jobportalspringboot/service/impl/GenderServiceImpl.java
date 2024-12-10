package com.lawencon.jobportalspringboot.service.impl;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportalspringboot.authentication.helper.SessionHelper;
import com.lawencon.jobportalspringboot.model.request.CreateGenderRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateGenderRequest;
import com.lawencon.jobportalspringboot.model.response.GenderResponse;
import com.lawencon.jobportalspringboot.persistance.entity.Gender;
import com.lawencon.jobportalspringboot.persistance.repository.GenderRepository;
import com.lawencon.jobportalspringboot.service.GenderService;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class GenderServiceImpl implements GenderService {

    private GenderRepository repository;

    @Override
    public void add(CreateGenderRequest request) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can create");
        }
        Gender item = new Gender();
        item.setIsActive(true);
        item.setVersion(0l);
        BeanUtils.copyProperties(request, item);
        repository.saveAndFlush(item);
    }

    @Override
    public void edit(String id, UpdateGenderRequest request) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can edit");
        }
        Gender item = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.valueOf(400), "Gender Not Found"));
        item.setCode(request.getCode());
        item.setName(request.getName());
        item.setIsActive(true);
        item.setVersion(item.getVersion() + 1);
        repository.saveAndFlush(item);
    }

    @Override
    public List<GenderResponse> findAll() {
        List<Gender> items = repository.findAll();
        List<GenderResponse> result = new ArrayList<>();
        for (Gender item : items) {
            GenderResponse roleResponse = masToResponse(item);
            result.add(roleResponse);
        }
        return result;
    }

    private GenderResponse masToResponse(Gender item) {
        GenderResponse response = new GenderResponse();
        BeanUtils.copyProperties(item, response);
        return response;
    }

    @Override
    public void delete(String id) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can delete");
        }
        Optional<Gender> genderOptional = repository.findById(id);

        if (genderOptional.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Gender not found");
        }
    }

    @Override
    public GenderResponse findByid(String id) {
       Gender item = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Gender not found"));
        return masToResponse(item);
    }

    @Override
    public Gender findEntityById(String id) {
        Gender item = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Gender not found"));
            return item;
    }
    
}
