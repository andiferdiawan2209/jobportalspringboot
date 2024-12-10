package com.lawencon.jobportalspringboot.model.request;


import com.lawencon.jobportalspringboot.validation.annotation.NotNullParam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateUserRequest {
        @NotNullParam
        private String username;

        @NotNullParam
        private String password;

        @NotNullParam
        private String email;

        @NotNullParam
        private String roleId; 
}