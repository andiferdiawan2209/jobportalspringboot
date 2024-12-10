package com.lawencon.jobportalspringboot.model.response;

import java.util.List;
import java.util.stream.Collectors;

import com.lawencon.jobportalspringboot.persistance.entity.JobTitle;
import com.lawencon.jobportalspringboot.persistance.entity.JobTitleDescription;
import com.lawencon.jobportalspringboot.persistance.entity.JobTitleSpesification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobTitleResponse {
    private String id;
    private String title;
    private List<String> descriptions;
    private List<String> spesifications;

    public JobTitleResponse(JobTitle jobTitle) {
        this.id = jobTitle.getId();
        this.title = jobTitle.getTitle();
        this.descriptions = jobTitle.getDescriptions().stream()
                .map(JobTitleDescription::getDescription)
                .collect(Collectors.toList());
        this.spesifications = jobTitle.getSpesifications().stream()
                .map(JobTitleSpesification::getDescription)
                .collect(Collectors.toList());

    }
}
