package com.example.WITHUS.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "TB_USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "USER_ID", length = 50)
    private String userId;

    @Column(name = "USER_NAME", length = 50, nullable = false)
    private String userName;

    @Column(name = "USER_NICK", length = 50, nullable = false)
    private String userNick;

    @Column(name = "USER_EMAIL", length = 50, nullable = false)
    private String userEmail;

    @Column(name = "USER_BIRTHDATE")
    private Date userBirthdate;

    @Column(name = "USER_ROLE", length = 10)
    private String userRole;

    @Column(name = "JOINED_AT")
    private Timestamp joinedAt;

    @Column(name = "USER_PASSWORD", length = 100, nullable = false)
    private String userPassword;
}



