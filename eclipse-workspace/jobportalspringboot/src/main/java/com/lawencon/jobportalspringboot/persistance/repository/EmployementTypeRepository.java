package com.lawencon.jobportalspringboot.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportalspringboot.persistance.entity.EmployementType;

@Repository
public interface EmployementTypeRepository extends JpaRepository<EmployementType,String> {
     boolean existsById(String id);
    boolean existsByCode(String code);

}
