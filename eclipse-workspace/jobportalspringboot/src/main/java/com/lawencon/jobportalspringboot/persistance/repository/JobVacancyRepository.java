package com.lawencon.jobportalspringboot.persistance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lawencon.jobportalspringboot.persistance.entity.JobVacancy;

public interface JobVacancyRepository extends JpaRepository<JobVacancy, String> {

}
