package com.example.sa_advanced.repository;

import com.example.sa_advanced.domain.Member;
import com.example.sa_advanced.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByModifiedAtDesc();

    List<Post> findAllByMember(Member member);
}