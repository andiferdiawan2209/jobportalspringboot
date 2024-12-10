package com.lawencon.jobportalspringboot.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class MasterEntity extends DeleteableEntity {
    @Column(name="is_active")
    private Boolean isActive;
}
