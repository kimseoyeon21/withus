package com.example.WITHUS.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "TB_CROOM", schema = "campus_24K_LI2_p2_6")
public class Croom {
    @Id
    @Column(name = "CROOM_IDX", nullable = false)
    private Integer id;

    @Column(name = "CROOM_TITLE", nullable = false, length = 500)
    private String croomTitle;

    @Column(name = "CREATED_AT", nullable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "CROOM_LIMIT", nullable = false)
    private Integer croomLimit;

    @Column(name = "CROOM_STATUS", nullable = false, length = 10)
    private String croomStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_IDX")
    private Team teamIdx;

    @OneToMany(mappedBy = "croomIdx")
    private Set<Chat> chats = new LinkedHashSet<>();

}