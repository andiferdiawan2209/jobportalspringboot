package com.lawencon.jobportalspringboot.persistance.entity;


import java.time.LocalDate;

import jakarta.persistence.Column;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
@Table(name = "tb_trx_job_vacancies")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tb_trx_job_vacancies SET deleted_at = now() WHERE id=? AND version=?")
@Where(clause = "deleted_at IS NULL")
public class TrxJobVacancy extends DeleteableEntity {
    
    @ManyToOne
    @JoinColumn(name = "job_vacancies_id", nullable = false)
     private JobVacancy jobvVacancy;

     @ManyToOne
    @JoinColumn(name = "recruiter_id", nullable = false)
     private User recruiter;

    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;

     @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;
}
