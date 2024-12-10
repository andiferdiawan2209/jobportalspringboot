package com.lawencon.jobportalspringboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.lawencon.jobportalspringboot.model.request.CreateCandidateRequest;
import com.lawencon.jobportalspringboot.model.request.CreateUserRequest;
import com.lawencon.jobportalspringboot.model.request.PagingRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateUserRequest;
import com.lawencon.jobportalspringboot.model.response.RoleResponse;
import com.lawencon.jobportalspringboot.model.response.UserResponse;
import com.lawencon.jobportalspringboot.persistance.entity.User;


public interface UserService {

    List<UserResponse> findAllByUsernameAndEmail(String name, String email);

    Page<UserResponse> findAll(PagingRequest pagingRequest, String inquiry);

    User saveUser(User user);
    
    User findEntityById(String id);

    User findById(String id);

    List<User> findAllUsers();

    Optional<User> getUserByUsernameAndPassword(String username, String password);

    UserDetailsService userDetailsService();

    void add(CreateUserRequest request);

    void createCandidate(CreateCandidateRequest request);

    void edit(String id, UpdateUserRequest request);
    
    List<UserResponse> findAll();

    void delete(String id);

    UserResponse findByEntityid(String id);

    void verify(String otpCode);
    
    void resendOtp(String email);
}
