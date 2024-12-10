package com.lawencon.jobportalspringboot.persistance.entity;



import java.time.LocalDate;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_profiles", uniqueConstraints = {
        @UniqueConstraint(name = "ck_profile", columnNames = { "user_id", "deleted_at" }) })
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tb_profiles SET deleted_at = now() WHERE id=? AND version=?")
@Where(clause = "deleted_at IS NULL")
public class Profile extends DeleteableEntity {
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "gender_id", nullable = true)
    private Gender gender;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "date_of_birth", nullable = true)
    private LocalDate dateBirth;

    @Column(name = "contact_number", nullable = true)
    private String phone;

    @Column(name = "address", nullable = true)
    private String address;
}
