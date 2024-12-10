package com.lawencon.jobportalspringboot.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobVacancyResponseByReqruiter {
    private String id; 
    private String jobTitle;
    private String location; 
    private String employmentType; 
    private String experienceLevel; 
    private Integer salaryMin; 
    private Integer salaryMax; 
    private String jobOverview; 
    private List<String> descriptions; 
    private List<String> specifications;
    private String status;
}
