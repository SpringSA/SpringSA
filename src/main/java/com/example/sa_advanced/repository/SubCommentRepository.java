package com.example.sa_advanced.repository;

import com.example.sa_advanced.domain.Comment;
import com.example.sa_advanced.domain.Member;
import com.example.sa_advanced.domain.SubComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCommentRepository extends JpaRepository<SubComment, Long> {
    List<SubComment> findAllByComment(Comment comment);
    List<SubComment> findAllByMember(Member member);



}
