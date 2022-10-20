package com.example.sa_advanced.service;

import com.example.sa_advanced.controller.response.CommentResponseDto;
import com.example.sa_advanced.controller.response.PostResponseDto;
import com.example.sa_advanced.controller.response.ResponseDto;
import com.example.sa_advanced.domain.*;
import com.example.sa_advanced.jwt.TokenProvider;
import com.example.sa_advanced.repository.CommentRepository;
import com.example.sa_advanced.repository.LikeCommentRepository;
import com.example.sa_advanced.repository.LikePostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeCommentService {
    private final CommentRepository commentRepository;
    private final LikeCommentRepository likeCommentRepository;
    private final TokenProvider tokenProvider;
//    @Transactional
    public ResponseDto<?> likeComment(Long comment_id, HttpServletRequest request){
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
        Comment comment = commentRepository.findById(comment_id).orElse(null);
        if(comment == null) return ResponseDto.fail("NOT_FOUND","게시글을 찾을 수 없습니다.");
        Optional<LikeComment> like = likeCommentRepository.findByMemberAndComment(member, comment);

        if(!like.isPresent()){
            // 좋아요
            LikeComment likeComment = new LikeComment();
            likeComment.setMember(member);
            likeComment.setComment(comment);
            comment.getLikeComments().add(likeComment);
            likeCommentRepository.save(likeComment);
        }
        else {
            likeCommentRepository.delete(like.get());
        }


        return ResponseDto.success(
                CommentResponseDto.builder()
                        .id(comment.getId())
                        .content(comment.getContent())
                        .author(comment.getMember().getUsername())
                        .likeCount(comment.countLike())
                        .createdAt(comment.getCreatedAt())
                        .modifiedAt(comment.getModifiedAt())
                        .build()
        );
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
