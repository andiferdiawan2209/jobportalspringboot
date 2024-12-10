package com.lawencon.jobportalspringboot.persistance.entity;

import jakarta.persistence.Column;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_statuses", uniqueConstraints = {
        @UniqueConstraint(name = "ck_status", columnNames = { "code", "deleted_at" }) })
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tb_statuses SET deleted_at = now() WHERE id=? AND version=?")
@Where(clause = "deleted_at IS NULL")
public class Status extends MasterEntity {
    @Column(name = "code", nullable=false)
    private String code;

    @Column(name="name", length=100)
    private String name;
}