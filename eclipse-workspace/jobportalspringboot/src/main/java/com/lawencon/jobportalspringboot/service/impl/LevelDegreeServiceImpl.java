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
import com.lawencon.jobportalspringboot.model.request.CreateLevelDegreeRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateLevelDegreeRequest;
import com.lawencon.jobportalspringboot.model.response.LevelDegreeResponse;
import com.lawencon.jobportalspringboot.persistance.entity.LevelDegree;
import com.lawencon.jobportalspringboot.persistance.repository.LevelDegreeRepository;
import com.lawencon.jobportalspringboot.service.LevelDegreeService;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class LevelDegreeServiceImpl implements LevelDegreeService {
    private LevelDegreeRepository repository;

    @Override
    public void add(CreateLevelDegreeRequest request) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can create");
        }
        LevelDegree item = new LevelDegree();
        item.setIsActive(true);
        item.setVersion(0l);
        BeanUtils.copyProperties(request, item);
        repository.saveAndFlush(item);
    }

    @Override
    public void edit(String id, UpdateLevelDegreeRequest request) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can edit");
        }
        LevelDegree item = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.valueOf(400), "LevelDegree Not Found"));
        item.setCode(request.getCode());
        item.setName(request.getName());
        item.setIsActive(true);
        item.setVersion(item.getVersion() + 1);
        repository.saveAndFlush(item);
    }

    @Override
    public List<LevelDegreeResponse> findAll() {
        List<LevelDegree> items = repository.findAll();
        List<LevelDegreeResponse> result = new ArrayList<>();
        for (LevelDegree item : items) {
            LevelDegreeResponse roleResponse = masToResponse(item);
            result.add(roleResponse);
        }
        return result;
    }

    private LevelDegreeResponse masToResponse(LevelDegree item) {
        LevelDegreeResponse response = new LevelDegreeResponse();
        BeanUtils.copyProperties(item, response);
        return response;
    }

    @Override
    public void delete(String id) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can delete");
        }
        Optional<LevelDegree> levelDegreeOptional = repository.findById(id);

        if (levelDegreeOptional.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"LevelDegree not found");
        }
    }

    @Override
    public LevelDegreeResponse findByid(String id) {
       LevelDegree item = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"LevelDegree not found"));
        return masToResponse(item);
    }

}
