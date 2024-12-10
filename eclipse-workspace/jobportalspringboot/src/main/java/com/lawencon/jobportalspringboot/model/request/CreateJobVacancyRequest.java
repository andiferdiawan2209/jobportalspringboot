package com.lawencon.jobportalspringboot.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobVacancyRequest {
    
    @NotBlank(message = "Job Title ID cannot be empty")
    private String jobTitleId;
    
    @NotBlank(message = "Location ID cannot be empty")
    private String locationId;
    
    @NotBlank(message = "Employment Type ID cannot be empty")
    private String employmentTypeId;
    
    @NotBlank(message = "Experience Level ID cannot be empty")
    private String experienceLevelId;
    
    @Min(value = 0, message = "Minimum salary cannot be negative")
    private Integer salaryMin;
    
    @Min(value = 0, message = "Maximum salary cannot be negative")
    private Integer salaryMax;
    
    private String jobOverview;
}
