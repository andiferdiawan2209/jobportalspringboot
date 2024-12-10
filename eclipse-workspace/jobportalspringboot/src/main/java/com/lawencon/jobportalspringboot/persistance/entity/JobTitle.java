package com.lawencon.jobportalspringboot.persistance.entity;


import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "job_titles", uniqueConstraints = {
        @UniqueConstraint(name = "ck_job_titles", columnNames = { "title", "deleted_at" }) })
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE job_titles SET deleted_at = now() WHERE id=? AND version=?")
@Where(clause = "deleted_at IS NULL")
public class JobTitle extends MasterEntity {
     @Column(name = "title", nullable=false)
     private String title;
    
    @OneToMany(mappedBy = "jobTitle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobTitleDescription> descriptions;

    @OneToMany(mappedBy = "jobTitle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobTitleSpesification> spesifications;
}
