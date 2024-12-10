package com.lawencon.jobportalspringboot.persistance.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportalspringboot.persistance.entity.Location;


@Repository
public interface LocationRepository extends JpaRepository<Location, String> {
    boolean existsById(String id);
    boolean existsByCode(String code);

}
