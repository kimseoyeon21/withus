package com.example.WITHUS.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "TB_COMMENT", schema = "campus_24K_LI2_p2_6")
public class Comment {
    @Id
    @Column(name = "CMT_IDX", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "COMM_IDX", nullable = false)
    private com.example.WITHUS.entity.Community commIdx;

    @Column(name = "CMT_CONTENT", nullable = false, length = 900)
    private String cmtContent;

    @Column(name = "CREATED_AT", nullable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

}