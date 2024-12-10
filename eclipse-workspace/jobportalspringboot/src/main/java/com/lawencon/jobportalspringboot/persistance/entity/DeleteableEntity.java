package com.lawencon.jobportalspringboot.persistance.entity;

import java.time.ZonedDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Setter
@Getter
public class DeleteableEntity extends AuditableEntity {

    @Column(name = "deleted_at")
    private ZonedDateTime deletedAt;
}
