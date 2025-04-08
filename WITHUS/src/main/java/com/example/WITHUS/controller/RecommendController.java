package com.example.WITHUS.controller;




import com.example.WITHUS.dto.RecommendDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recommend")
public class RecommendController {

    private final String PYTHON_API_URL = "https://jdy.onrender.com/api/recommend/teams";  // Python 서버 URL

    @PostMapping
    public ResponseEntity<?> getTeamRecommendations(@RequestBody RecommendDto requestDto) {
        try {
            // 사용자 정보 추출
            Map<String, Object> user = new HashMap<>();
            user.put("skills", List.of(requestDto.getUser().getUserSkill().split(",")));
            user.put("region", requestDto.getUser().getUserRegion());
            user.put("target", requestDto.getUser().getUserTarget());

            // 팀 리스트 구성
            List<Map<String, Object>> teams = new ArrayList<>();
            requestDto.getTeams().forEach(team -> {
                Map<String, Object> t = new HashMap<>();
                t.put("team_id", team.getTeamId());
                t.put("recruitment_skill", team.getSkill());
                t.put("region", team.getRegion());
                t.put("goal", team.getTarget());
                teams.add(t);
            });

            // Python 서버에 전달할 전체 요청 객체 구성
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("user", user);
            requestBody.put("teams", teams);

            // RestTemplate으로 Python 서버 호출
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Object> response = restTemplate.postForEntity(PYTHON_API_URL, requestBody, Object.class);

            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }

    }
}

