package com.example.WITHUS.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "TB_TEAM", schema = "campus_24K_LI2_p2_6")
public class Team {
    @Id
    @Column(name = "TEAM_IDX", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CONT_IDX", nullable = false)
    private Contest contIdx;

    @Column(name = "TEAM_TITLE", nullable = false)
    private String teamTitle;

    @Lob
    @Column(name = "TEAM_INFO", nullable = false)
    private String teamInfo;

    @Column(name = "TEAM_LIMIT", nullable = false)
    private Integer teamLimit;

    @Column(name = "SKILL", nullable = false, length = 500)
    private String skill;

    @Column(name = "REGION", nullable = false, length = 100)
    private String region;

    @Column(name = "TARGET", nullable = false, length = 100)
    private String target;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "CLOSED_AT", nullable = false)
    private Instant closedAt;

    @Column(name = "CREATED_AT", nullable = false)
    private Instant createdAt;

}