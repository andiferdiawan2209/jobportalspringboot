package com.lawencon.jobportalspringboot.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLocationRequest extends CreateMasterRequest {

     @NotBlank(message = "Is Active Cannot be empty")
     private Boolean isActive;
    
}
