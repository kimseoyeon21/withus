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
@Table(name = "TB_COMMUNITY", schema = "campus_24K_LI2_p2_6")
public class Community {
    @Id
    @Column(name = "COMM_IDX", nullable = false)
    private Integer id;

    @Column(name = "COMM_TITLE", nullable = false, length = 1000)
    private String commTitle;

    @Lob
    @Column(name = "COMM_CONTENT", nullable = false)
    private String commContent;

    @Column(name = "COMM_FILE", length = 1000)
    private String commFile;

    @Column(name = "CREATED_AT", nullable = false)
    private Instant createdAt;

    @Column(name = "COMM_LIKES", nullable = false)
    private Integer commLikes;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @OneToMany(mappedBy = "commIdx")
    private Set<Comment> tbComments = new LinkedHashSet<>();

}