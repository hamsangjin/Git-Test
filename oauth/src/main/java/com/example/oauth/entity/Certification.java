package com.example.oauth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "certification")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Certification {
    @Id
    @Column(name = "user_id")
    private String userId;
    private String email;
    @Column(name = "certification_number")
    private String certificationNumber;
}
