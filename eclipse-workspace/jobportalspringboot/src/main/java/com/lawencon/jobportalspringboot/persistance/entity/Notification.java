package com.lawencon.jobportalspringboot.persistance.entity;



import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_notifications")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tb_notifications SET deleted_at = now() WHERE id=? AND version=?")
@Where(clause = "deleted_at IS NULL")
public class Notification extends DeleteableEntity {
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="message", length=100)
    private String message;

    @ManyToOne
    @JoinColumn(name="trx_vacancy_id")
    private TrxJobVacancy trxJobVacancy;
}
