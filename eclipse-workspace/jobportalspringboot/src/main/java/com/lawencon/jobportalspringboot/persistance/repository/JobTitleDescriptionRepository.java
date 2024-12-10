package com.lawencon.jobportalspringboot.persistance.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportalspringboot.persistance.entity.JobTitleDescription;

@Repository
public interface JobTitleDescriptionRepository extends JpaRepository<JobTitleDescription, String> {
    void deleteByJobTitleId(String id);
}
