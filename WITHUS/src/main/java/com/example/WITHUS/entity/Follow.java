package com.example.WITHUS.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_FOLLOW", schema = "campus_24K_LI2_p2_6")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FOLLOW_IDX", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FOLLOWER", nullable = false)
    private Profile follower;

    @Column(name = "FOLLOWEE", nullable = false, length = 50)
    private String followee;

    @Column(name = "FOLLOWED_AT", nullable = false, length = 50)
    private String followedAt;

}