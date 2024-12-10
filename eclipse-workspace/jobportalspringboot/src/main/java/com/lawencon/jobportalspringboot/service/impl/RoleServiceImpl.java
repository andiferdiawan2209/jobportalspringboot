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
import com.lawencon.jobportalspringboot.model.request.CreateRoleRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateRoleRequest;
import com.lawencon.jobportalspringboot.model.response.RoleResponse;
import com.lawencon.jobportalspringboot.persistance.entity.Role;
import com.lawencon.jobportalspringboot.persistance.repository.RoleRepository;
import com.lawencon.jobportalspringboot.service.RoleService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public void add(CreateRoleRequest request) {
String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can create");
        }
        Role role = new Role();
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        role.setIsActive(true);
        role.setVersion(0l);
        BeanUtils.copyProperties(request, role);
        roleRepository.saveAndFlush(role);
    }

    @Override
    public void edit(String id, UpdateRoleRequest request) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can edit");
        }
        Role item = roleRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Role not found"));
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        item.setCode(request.getCode());
        item.setName(request.getName());
        item.setIsActive(true);
        item.setVersion(item.getVersion() + 1);
        roleRepository.saveAndFlush(item);
    }

    @Override
    public List<RoleResponse> findAll() {
        List<Role> roles = roleRepository.findAll();
        List<RoleResponse> result = new ArrayList<>();
        for (Role role : roles) {
            RoleResponse roleResponse = masToResponse(role);
            result.add(roleResponse);
        }
        return result;
    }

    private RoleResponse masToResponse(Role role) {
        RoleResponse response = new RoleResponse();
        BeanUtils.copyProperties(role, response);
        return response;
    }


    @Override
    public void delete(String id) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can delete");
        }
          Optional<Role> genderOptional = roleRepository.findById(id);
        if (genderOptional.isPresent()) {
            roleRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Role not found");
        }
    }

    @Override
    public RoleResponse findByid(String id) {
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Role not found"));
        return masToResponse(role);
    }

    @Override
    public Role findEntityById(String id) {
        Role item = roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Role not found"));
            return item;
    }

    @Override
    public List<Role> findAllRole() {
        return roleRepository.findAll();

    }

    @Override
    public Role findByRoleId(String id) {
        Role item = roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
            return item;
    }
    
}
