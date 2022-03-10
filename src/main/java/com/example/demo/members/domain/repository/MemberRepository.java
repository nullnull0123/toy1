package com.example.demo.members.domain.repository;

import com.example.demo.members.domain.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Members, Long> {
    public Optional<Members> findByUsername(String username);
}
