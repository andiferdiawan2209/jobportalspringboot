package com.lawencon.jobportalspringboot.model.request;

import com.lawencon.jobportalspringboot.validation.annotation.NotNullParam;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
   
     @NotNullParam
        private String username;

        @NotNullParam
        private String password;

        @NotNullParam
        private String email;

        @NotNullParam
        private String roleId;
        
     @NotBlank(message = "Is Active Cannot be empty")
     private Boolean isActive;

}
