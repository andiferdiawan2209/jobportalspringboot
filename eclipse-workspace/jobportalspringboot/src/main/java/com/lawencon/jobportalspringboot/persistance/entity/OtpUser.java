package com.lawencon.jobportalspringboot.persistance.entity;

import java.time.ZonedDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "tb_otp_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE tb_otp_users SET deleted_at = now() WHERE id=? AND version=?")
@Where(clause = "deleted_at IS NULL")
public class OtpUser extends DeleteableEntity {
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "code", unique = true, length = 100)
    private String code;

    @Column(name = "expire", nullable = false)
    private ZonedDateTime expire;
}
