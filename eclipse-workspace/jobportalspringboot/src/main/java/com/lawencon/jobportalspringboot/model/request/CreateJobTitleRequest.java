package com.lawencon.jobportalspringboot.model.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobTitleRequest {
    @NotBlank(message = "Code cannot be empty")
    private String title;

    @NotBlank(message = "Code cannot be empty")
    private List<String> descriptions;

    @NotBlank(message = "Code cannot be empty")
    private List<String> spesifications;
}
