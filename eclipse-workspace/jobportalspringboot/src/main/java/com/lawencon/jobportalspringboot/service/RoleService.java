package com.lawencon.jobportalspringboot.service;

import java.util.List;

import com.lawencon.jobportalspringboot.model.request.CreateRoleRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateRoleRequest;
import com.lawencon.jobportalspringboot.model.response.RoleResponse;
import com.lawencon.jobportalspringboot.persistance.entity.Role;

public interface RoleService {

    void add(CreateRoleRequest request);

    void edit(String id, UpdateRoleRequest request);
    
    List<RoleResponse> findAll();

    void delete(String id);

    RoleResponse findByid(String id);

    Role findEntityById(String id);

    List<Role> findAllRole();

    Role findByRoleId(String id);
}
