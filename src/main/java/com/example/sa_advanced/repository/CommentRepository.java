package com.example.sa_advanced.repository;


import com.example.sa_advanced.domain.Comment;
import com.example.sa_advanced.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post);
}