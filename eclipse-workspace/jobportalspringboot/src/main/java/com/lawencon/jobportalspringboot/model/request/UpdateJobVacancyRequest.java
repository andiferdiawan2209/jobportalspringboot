package com.lawencon.jobportalspringboot.model.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateJobVacancyRequest {
     @NotNull(message = "Job title ID cannot be null")
    private String jobTitleId;

    @NotNull(message = "Location ID cannot be null")
    private String locationId;

    @NotNull(message = "Employment type ID cannot be null")
    private String employmentTypeId; 

    @NotNull(message = "Experience level ID cannot be null")
    private String experienceLevelId; 

    private Integer salaryMin; 
    private Integer salaryMax; 

    @NotBlank(message = "Job overview cannot be empty")
    private String jobOverview;

}
