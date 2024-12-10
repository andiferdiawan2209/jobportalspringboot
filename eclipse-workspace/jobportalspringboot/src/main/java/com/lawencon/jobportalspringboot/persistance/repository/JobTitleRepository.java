package com.lawencon.jobportalspringboot.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportalspringboot.persistance.entity.JobTitle;

@Repository
public interface JobTitleRepository extends JpaRepository<JobTitle, String> {

}
