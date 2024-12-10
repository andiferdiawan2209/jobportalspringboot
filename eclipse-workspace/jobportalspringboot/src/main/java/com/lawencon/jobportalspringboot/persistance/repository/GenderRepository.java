package com.lawencon.jobportalspringboot.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportalspringboot.persistance.entity.Gender;

@Repository
public interface GenderRepository extends JpaRepository<Gender,String> {
     boolean existsById(String id);
    boolean existsByCode(String code);
}