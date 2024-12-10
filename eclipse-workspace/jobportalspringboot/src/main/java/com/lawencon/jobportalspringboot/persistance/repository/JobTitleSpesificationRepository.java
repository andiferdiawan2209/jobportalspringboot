package com.lawencon.jobportalspringboot.persistance.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportalspringboot.persistance.entity.JobTitleSpesification;

@Repository
public interface JobTitleSpesificationRepository extends JpaRepository<JobTitleSpesification, String> {
    void deleteByJobTitleId(String id);
}