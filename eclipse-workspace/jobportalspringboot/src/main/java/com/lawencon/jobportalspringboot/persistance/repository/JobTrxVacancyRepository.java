package com.lawencon.jobportalspringboot.persistance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lawencon.jobportalspringboot.persistance.entity.TrxJobVacancy;

public interface JobTrxVacancyRepository extends JpaRepository<TrxJobVacancy, String> {
    List<TrxJobVacancy> findByRecruiterId(String recruiterId);
}
