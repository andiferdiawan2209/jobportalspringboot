package com.lawencon.jobportalspringboot.persistance.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportalspringboot.persistance.entity.LevelDegree;


@Repository
public interface LevelDegreeRepository extends JpaRepository<LevelDegree, String> {
    boolean existsById(String id);
    boolean existsByCode(String code);

}
