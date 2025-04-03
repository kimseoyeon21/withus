package com.example.WITHUS.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "TB_CONTEST", schema = "campus_24K_LI2_p2_6")
public class Contest {
    @Id
    @Column(name = "CONT_IDX", nullable = false)
    private Integer id;

    @Column(name = "CONT_NAME", nullable = false)
    private String contName;

    @Lob
    @Column(name = "CONT_CONTENT", nullable = false)
    private String contContent;

    @Column(name = "CONT_ORG", nullable = false)
    private String contOrg;

    @Column(name = "ST_DT", nullable = false)
    private LocalDate stDt;

    @Column(name = "ED_DT", nullable = false)
    private LocalDate edDt;

    @Lob
    @Column(name = "CONT_PRIZE", nullable = false)
    private String contPrize;

    @Column(name = "CONT_POSTER", length = 1000)
    private String contPoster;

    @Column(name = "CREATED_AT", nullable = false)
    private Instant createdAt;

}