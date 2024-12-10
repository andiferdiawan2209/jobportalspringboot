package com.lawencon.jobportalspringboot.model.request;

import com.lawencon.jobportalspringboot.validation.annotation.NotBlankParam;
import com.lawencon.jobportalspringboot.validation.annotation.NotNullParam;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CreateMasterRequest {
    
    @NotBlank(message = "Code cannot be empty")
    private String code;


   @NotBlank(message = "Name cannot be empty")
    private String name;

}
