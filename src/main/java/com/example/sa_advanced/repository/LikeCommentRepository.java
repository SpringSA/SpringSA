package com.example.sa_advanced.repository;

import com.example.sa_advanced.domain.Comment;
import com.example.sa_advanced.domain.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    List<LikeComment> findAllByComment(Comment comment);

}
