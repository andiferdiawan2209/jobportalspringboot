package com.lawencon.jobportalspringboot.persistance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportalspringboot.persistance.entity.OtpUser;
import com.lawencon.jobportalspringboot.persistance.entity.Profile;
import com.lawencon.jobportalspringboot.persistance.entity.User;

@Repository
public interface OtpUserRepository extends JpaRepository<OtpUser, String> {
    Optional<OtpUser> findByCode(String code);
    void deleteByUser(User user);
}
