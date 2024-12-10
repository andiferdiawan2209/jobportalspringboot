package com.lawencon.jobportalspringboot.persistance.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportalspringboot.persistance.entity.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    boolean existsById(String id);

    boolean existsByCode(String code);
    Optional<Role> findByCode(String code);
}
