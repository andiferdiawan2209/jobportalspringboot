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
import com.lawencon.jobportalspringboot.model.request.CreateRoleRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateRoleRequest;
import com.lawencon.jobportalspringboot.model.response.RoleResponse;
import com.lawencon.jobportalspringboot.model.response.WebResponse;
import com.lawencon.jobportalspringboot.service.RoleService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Role", description="Role API")
@RestController
@RequestMapping({ "/api/v1" })
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping(value = "/role", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<RoleResponse>>> findAll() {
        return ResponseEntity.ok(ResponseHelper.ok(roleService.findAll()));
    }

    @PostMapping(value = "/role", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<RoleResponse>> add(@Valid @RequestBody CreateRoleRequest request) {
        roleService.add(request);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    @PutMapping(value = "/role/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<RoleResponse>> edit(@PathVariable String id, @Valid @RequestBody UpdateRoleRequest request) {
        roleService.edit(id, request);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    @GetMapping(value = "/role/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<RoleResponse>> findById(@PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(roleService.findByid(id)));
    }

    @DeleteMapping(value = "/role/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<RoleResponse>> deleteById(@PathVariable String id) {
        roleService.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok());
    }


}
