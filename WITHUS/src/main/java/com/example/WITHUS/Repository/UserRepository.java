package com.example.WITHUS.Repository;

import com.example.WITHUS.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findById(String userId); // 필드명과 정확히 일치

}

