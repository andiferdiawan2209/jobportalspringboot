package com.lawencon.jobportalspringboot.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportalspringboot.persistance.entity.Profile;


@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {

}

