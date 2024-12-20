package com.lawencon.jobportalspringboot.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenderResponse {
    private String id;
    private String code;
    private String name;
    private Boolean isActive;
    private Long version;
}
