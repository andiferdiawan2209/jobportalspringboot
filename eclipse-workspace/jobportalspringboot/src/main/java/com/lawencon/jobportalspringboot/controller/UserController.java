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
import com.lawencon.jobportalspringboot.model.request.CreateUserRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateUserRequest;
import com.lawencon.jobportalspringboot.model.response.UserResponse;
import com.lawencon.jobportalspringboot.model.response.WebResponse;
import com.lawencon.jobportalspringboot.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;


@Tag(name = "User", description="User API")
@RestController
@RequestMapping({ "/api/v1" })
@AllArgsConstructor
public class UserController {
    

    private final UserService userService;


    @GetMapping(value = "/user", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<UserResponse>>> findAll() {
        return ResponseEntity.ok(ResponseHelper.ok(userService.findAll()));
    }

    @GetMapping(value = "user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<UserResponse>> findById(@PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(userService.findByEntityid(id)));
    }

    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<Void>> add(@RequestBody CreateUserRequest request) {
        userService.add(request);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    @PutMapping(value = "user/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<Void>> edit(@PathVariable String id, @RequestBody UpdateUserRequest request) {
        userService.edit(id, request);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

    @DeleteMapping(value = "user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<Void>> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok());
    }
    
}
