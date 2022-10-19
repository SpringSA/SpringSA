package com.example.sa_advanced.repository;

import com.example.sa_advanced.domain.LikePost;
import com.example.sa_advanced.domain.Member;
import com.example.sa_advanced.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    List<LikePost> findAllByPost(Post post);
    List<LikePost> deleteLikePostBy();
    Optional<LikePost> findByMemberAndPost(Member member, Post post);
}