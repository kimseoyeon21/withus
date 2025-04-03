package com.example.WITHUS.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_PROFILE", schema = "campus_24K_LI2_p2_6")
public class Profile {
    @Id
    @Column(name = "USER_ID", nullable = false, length = 50)
    private String userId;

    @Column(name = "USER_SKILL", nullable = false, length = 500)
    private String userSkill;

    @Column(name = "USER_REGION", nullable = false, length = 100)
    private String userRegion;

    @Column(name = "USER_TARGET", nullable = false, length = 100)
    private String userTarget;

    @Column(name = "PROFILE_IMG", length = 1000)
    private String profileImg;

}