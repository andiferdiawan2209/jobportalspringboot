package com.lawencon.jobportalspringboot.persistance.entity;



import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_job_vacancies")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tb_job_vacancies SET deleted_at = now() WHERE id=? AND version=?")
@Where(clause = "deleted_at IS NULL")
public class JobVacancy extends DeleteableEntity {
    
    @ManyToOne
    @JoinColumn(name = "job_title_id", nullable = false)
    private JobTitle jobTitle;

    @ManyToOne
    @JoinColumn(name = "job_location_id", nullable = false)
    private Location location;

    @ManyToOne
    @JoinColumn(name = "employment_type_id", nullable = false)
    private EmployementType employementType;

    @ManyToOne
    @JoinColumn(name = "experience_level_id", nullable = false)
    private ExperianceLevel experianceLevel;

     @Column(name = "salary_range_min", nullable = true)
     private Integer salaryMin;

     @Column(name = "salary_range_max", nullable = true)
     private Integer salaryMax;

     @Column(name = "job_overview", nullable = true)
    private String jobOverview;
}
