package com.lawencon.jobportalspringboot.model.response;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobVacancyTrxResponse {
    private String id; 
    private String jobTitle;
    private String recruiter;
    private LocalDate publishDate;
    private String status;
}
