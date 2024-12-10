package com.lawencon.jobportalspringboot.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateNotifResponse {
    private String message;
    private String jobTitle;
    private String location;
    private String salaryMax; 
    private String salaryMin; 
}
