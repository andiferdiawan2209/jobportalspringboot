package com.lawencon.jobportalspringboot.model.request;

import com.lawencon.jobportalspringboot.validation.annotation.NotNullParam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerifyRequest {
    @NotNullParam
    private String code;
}
