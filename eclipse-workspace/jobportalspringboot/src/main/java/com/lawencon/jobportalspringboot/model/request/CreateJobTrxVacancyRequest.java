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
public class CreateJobTrxVacancyRequest {
    @NotBlank(message = "Code cannot be empty")
    private String vacancyId;
    @NotBlank(message = "Code cannot be empty")
    private String recruiterId;
    @NotBlank(message = "Code cannot be empty")
    private String statusId;
    @NotBlank(message = "Code cannot be empty")
    private String publishDate;
}
