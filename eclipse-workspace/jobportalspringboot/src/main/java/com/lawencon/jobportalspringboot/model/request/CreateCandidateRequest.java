package com.lawencon.jobportalspringboot.model.request;
import com.lawencon.jobportalspringboot.validation.annotation.NotNullParam;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateCandidateRequest {
        @NotNullParam
        private String username;

        @NotNullParam
        private String password;

        @NotNullParam
        private String email;
        @NotNullParam
        private String genderId;
        @NotNullParam
        private String fullName;
}
