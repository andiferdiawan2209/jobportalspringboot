package com.lawencon.jobportalspringboot.persistance.entity;



import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_employment_types", uniqueConstraints = {
        @UniqueConstraint(name = "ck_employment_types", columnNames = { "code", "deleted_at" }) })
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tb_employment_types SET deleted_at = now() WHERE id=? AND version=?")
@Where(clause = "deleted_at IS NULL")
public class EmployementType extends MasterEntity {
     @Column(name = "code", nullable=false)
    private String code;

    @Column(name="name", length=100)
    private String name;
}
