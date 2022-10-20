package com.example.sa_advanced.repository;

import com.example.sa_advanced.domain.Comment;
import com.example.sa_advanced.domain.LikeComment;
import com.example.sa_advanced.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    List<LikeComment> findAllByComment(Comment comment);


    Optional<LikeComment> findByMemberAndComment(Member member, Comment comment);

    List<LikeComment> findAllByMember(Member member);
//    Long countAllByCommentId(Long commentId);

}
