package com.lawencon.jobportalspringboot.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportalspringboot.authentication.helper.SessionHelper;
import com.lawencon.jobportalspringboot.model.request.CreateExperianceLevelRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateExperianceLevelRequest;
import com.lawencon.jobportalspringboot.model.response.ExperianceLevelResponse;
import com.lawencon.jobportalspringboot.persistance.entity.ExperianceLevel;
import com.lawencon.jobportalspringboot.persistance.repository.ExperianceLevelRepository;
import com.lawencon.jobportalspringboot.service.ExperianceLevelService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExperianceLevelServiceImpl implements ExperianceLevelService {

    private ExperianceLevelRepository repository;

    @Override
    public void add(CreateExperianceLevelRequest request) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can create");
        }
        ExperianceLevel item = new ExperianceLevel();
        item.setIsActive(true);
        item.setVersion(0l);
        BeanUtils.copyProperties(request, item);
        repository.saveAndFlush(item);
    }

    @Override
    public void edit(String id, UpdateExperianceLevelRequest request) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can edit");
        }
        ExperianceLevel item = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.valueOf(400), "ExperianceLevel Not Found"));
        item.setCode(request.getCode());
        item.setName(request.getName());
        item.setIsActive(true);
        item.setVersion(item.getVersion() + 1);
        repository.saveAndFlush(item);
    }

    @Override
    public List<ExperianceLevelResponse> findAll() {
        List<ExperianceLevel> items = repository.findAll();
        List<ExperianceLevelResponse> result = new ArrayList<>();
        for (ExperianceLevel item : items) {
            ExperianceLevelResponse roleResponse = masToResponse(item);
            result.add(roleResponse);
        }
        return result;
    }

    private ExperianceLevelResponse masToResponse(ExperianceLevel item) {
        ExperianceLevelResponse response = new ExperianceLevelResponse();
        BeanUtils.copyProperties(item, response);
        return response;
    }

    @Override
    public void delete(String id) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can delete");
        }
        Optional<ExperianceLevel> experianceLevelOptional = repository.findById(id);

        if (experianceLevelOptional.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"ExperianceLevel not found");
        }
    }

    @Override
    public ExperianceLevelResponse findByid(String id) {
       ExperianceLevel item = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"ExperianceLevel not found"));
        return masToResponse(item);
    }
}
