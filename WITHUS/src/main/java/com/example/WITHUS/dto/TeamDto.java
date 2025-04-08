package com.example.WITHUS.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TeamDto {
    private int teamId;            // 팀 ID
    private String skill; // 팀의 모집 기술
    private String region;         // 팀의 지역
    private String target;
}